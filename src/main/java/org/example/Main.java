package org.example;

import org.bson.types.ObjectId;
import org.example.db.DrugStoreMongoConnector;
import org.example.service.DrugStoreService;


public class Main {
    public static void main(String[] args) {
        try (DrugStoreMongoConnector ignored = DrugStoreMongoConnector.getInstance()){
            var service = new DrugStoreService();

            System.out.println(service.orderCost(new ObjectId("65d10615f534b8594eb5acdd")));
        }
    }
}