package org.example.service;
import com.mongodb.BasicDBObject;
import com.mongodb.MongoException;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import org.bson.types.ObjectId;
import org.example.db.DrugStoreMongoConnector;
import org.example.model.*;
import org.example.util.ObjectToBsonConverter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.mongodb.client.model.Filters.*;

public class DrugStoreService {
    private final DrugStoreMongoConnector db;
    public DrugStoreService() {
        db = DrugStoreMongoConnector.getInstance();
    }

    public List<Category> categories() {
        return db.categories().find().into(new ArrayList<>());
    }
    public List<Medicine> medicines() {
        return db.medicines().find().into(new ArrayList<>());
    }
    public List<Order> orders() {
        return db.orders().find().into(new ArrayList<>());
    }
    public List<Medicine> medicineByCategory(String category) {
        var categoryObj = db.categories()
                .find(eq(Category.NAME_FIELD, category))
                .first();
        if (categoryObj == null) {
            throw new MongoException("Category not found");
        }

        var categoryId = categoryObj.getId();
        var categoryFilter = eq(Medicine.CATEGORY_ID_FIELD, categoryId);

        return db.medicines()
                .find(categoryFilter)
                .into(new ArrayList<>());
    }
    public List<Medicine> medicinesByTitleLike(String title) {
        var filter = Filters.regex(Medicine.TITLE_FIELD, title, "i");

        return db.medicines()
                .find(filter)
                .into(new ArrayList<>());
    }
    public List<Medicine> medicinesByPriceGreaterOrEquals(double lower) {
        return medicinesByPriceRangeOrEquals(lower, Double.MAX_VALUE);
    }
    public List<Medicine> medicinesByPriceLowerOrEquals(double upper) {
        return medicinesByPriceRangeOrEquals(-Double.MAX_VALUE, upper);
    }
    public List<Medicine> medicinesByPriceRangeOrEquals(double lower, double upper) {
        return db.medicines()
                .find(and(
                        lte(Medicine.PRICE_FIELD, upper),
                        gte(Medicine.PRICE_FIELD, lower
                        )
                ))
                .into(new ArrayList<>());
    }
    public List<Medicine> medicinesAvailableInStore() {
        return db.medicines()
                .find(gte(Medicine.COUNT_FIELD, 1))
                .into(new ArrayList<>());
    }
    //Реализовать функционал добавления товаров в корзину и подсчета общей стоимости заказа;
    public void addProductInCart(ObjectId clientId, ProductInOrder product) {
        //предполагается, что в бд есть 1 или 0 документов
        //в коллекции orders с айди клиента
        //таких, что CategoryId = категории "Собирается клиентом"
        var collectingByClientStatus = db.statuses()
                .find(eq(Status.NAME_FIELD, Status.COLLECTING_BY_CLIENT_NAME_FIELD))
                .first();
        if(collectingByClientStatus == null) {
            throw new MongoException("Can't find correct status in DB");
        }
        var collectingByClientStatusId = collectingByClientStatus.getId();

        var findFilter = new BasicDBObject(Map.of(
                    Order.CLIENT_ID_FIELD, clientId,
                    Order.STATUS_ID_FIELD, collectingByClientStatusId
        ));
        var clientCart = db.orders()
                .find(findFilter)
                .into(new ArrayList<>());
        if(clientCart.size() > 1) {
            throw new MongoException("Client has more than one cart");
        }

        var orderBuilder = Order.builder()
                .clientId(clientId)
                .statusId(collectingByClientStatus.getId());
        if(clientCart.isEmpty()) {
            //в таблице заказов создать новую запись с новым продуктом
            var order = orderBuilder
                    .productsInOrder(List.of(product))
                    .statusChangeTime(LocalDateTime.now())
                    .build();

            db.orders().insertOne(order);
        } else {
            var order = orderBuilder.build();
            var orderToFind = ObjectToBsonConverter.fromOrder(order);
            var foundedOrder = db.orders()
                    .find(orderToFind)
                    .into(new ArrayList<>()).getFirst();

            addProduct(foundedOrder, product);

            var query = ObjectToBsonConverter.fromOrder(Order.builder()
                            .id(foundedOrder.getId())
                            .build());

            var update = Updates.combine(
                    Updates.set(Order.PRODUCTS_FIELD, foundedOrder.getProductsInOrder()),
                    Updates.set(Order.STATUS_CHANGED_TIME_FIELD, LocalDateTime.now()));

            db.orders().updateOne(query, update);
        }
    }

    private void addProduct(Order order, ProductInOrder productToAdd) {
        var orderProducts = order.getProductsInOrder();
        for (ProductInOrder product : orderProducts) {
            if (product.getId().equals(productToAdd.getId())) {
                product.setAmount(product.getAmount() + productToAdd.getAmount());
                return;
            }
        }
        orderProducts.add(productToAdd);
    }
}