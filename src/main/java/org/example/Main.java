package org.example;

import org.bson.types.ObjectId;
import org.example.db.DrugStoreMongoConnector;
import org.example.model.ProductInOrder;
import org.example.service.DrugStoreService;

public class Main {
    public static void main(String[] args) {
        try (DrugStoreMongoConnector ignored = DrugStoreMongoConnector.getInstance()){
            var service = new DrugStoreService();

            var medicines = service.medicinesAvailableInStore();
            var orders = service.orders();
            service.addProductInCart(
                    new ObjectId("65cb49057e30a8d3f4e8066e"),
                    new ProductInOrder(new ObjectId("65cb56ebb780aeec2c71fb8b"),2));
            System.out.println("MEDICINE");
            medicines.forEach(System.out::println);

            System.out.println("ORDERS");
            orders.forEach(System.out::println);
        }
    }
}