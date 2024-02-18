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
import java.util.Optional;

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
        var collectingByClientStatusId = collectingByClientStatusId();

        var clientCart = clientCart(clientId, collectingByClientStatusId);

        if(clientCart.isEmpty()) {
            createCartWithProduct(clientId, collectingByClientStatusId, product);
        } else {    
            insertProductInDb(clientId, collectingByClientStatusId, product);
        }
    }

    public double orderCost(ObjectId orderId) {
        var order = findOrder(orderId);

        if(order.isEmpty()) {
            throw new MongoException("Order does not exist");
        }

        double cost = 0;
        for (var product: order.get().getProductsInOrder()) {
            var medicine = medicine(product.getId());
            if (medicine.isEmpty()) {
                throw new MongoException("Medicine does not exist");
            }

            cost += product.getAmount() * medicine.get().getPrice();
        }
        return cost;
    }

    private Optional<Medicine> medicine(ObjectId id) {
        var filter = new BasicDBObject(Medicine.ID_FIELD, id);
        var medicine = db.medicines()
                .find(filter)
                .into(new ArrayList<>());
        if(medicine.isEmpty()) {
            return Optional.empty();
        } else {
            return Optional.of(medicine.getFirst());
        }
    }

    private Optional<Order> findOrder(ObjectId orderId) {
        var filter = new BasicDBObject(Order.ID_FIELD, orderId);

        var order = db.orders()
                .find(filter)
                .into(new ArrayList<>());
        if(order.isEmpty()) {
            return Optional.empty();
        } else {
            return Optional.of(order.getFirst());
        }
    }

    private void insertProductInDb(ObjectId clientId, ObjectId collectingByClientStatusId, ProductInOrder product) {
        var order = Order.builder()
                .clientId(clientId)
                .statusId(collectingByClientStatusId)
                .build();
        var bsonOrder = ObjectToBsonConverter.fromOrder(order);

        var foundedOrder = db.orders()
                .find(bsonOrder)
                .into(new ArrayList<>())
                .getFirst();

        addProduct(foundedOrder, product);

        var query = ObjectToBsonConverter.fromOrder(Order.builder()
                .id(foundedOrder.getId())
                .build());

        var update = Updates.combine(
                Updates.set(Order.PRODUCTS_FIELD, foundedOrder.getProductsInOrder()),
                Updates.set(Order.STATUS_CHANGED_TIME_FIELD, LocalDateTime.now()));

        db.orders().updateOne(query, update);
    }

    private void createCartWithProduct(
            ObjectId clientId,
            ObjectId collectingByClientStatusId,
            ProductInOrder product
    ) {
        var order = Order.builder()
                .clientId(clientId)
                .statusId(collectingByClientStatusId)
                .statusChangeTime(LocalDateTime.now())
                .build();
        addProduct(order, product);

        db.orders().insertOne(order);
    }

    private Optional<Order> clientCart(ObjectId clientId, ObjectId collectingByClientStatusId) {
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

        if(clientCart.isEmpty()) {
            return Optional.empty();
        } else {
            return Optional.of(clientCart.getFirst());
        }
    }

    private ObjectId collectingByClientStatusId() {
        var collectingByClientStatus = db.statuses()
                .find(eq(Status.NAME_FIELD, Status.COLLECTING_BY_CLIENT_NAME_FIELD))
                .first();
        if(collectingByClientStatus == null) {
            throw new MongoException("Can't find correct status in DB");
        }

        return collectingByClientStatus.getId();
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
