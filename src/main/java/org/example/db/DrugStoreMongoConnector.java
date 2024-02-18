package org.example.db;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.example.model.*;

import java.io.Closeable;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

public class DrugStoreMongoConnector implements Closeable {
    private static DrugStoreMongoConnector instance;
    private final MongoDatabase database;
    private final MongoClient mongoClient;

    private DrugStoreMongoConnector() {
        ConnectionString connectionString = new ConnectionString("mongodb://localhost:27017");
        CodecRegistry pojoCodecRegistry = fromProviders(PojoCodecProvider
                .builder()
                .automatic(true)
                .build());
        CodecRegistry codecRegistry = fromRegistries(MongoClientSettings.getDefaultCodecRegistry(), pojoCodecRegistry);

        MongoClientSettings clientSettings = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .codecRegistry(codecRegistry)
                .build();

        mongoClient = MongoClients.create(clientSettings);
        database = mongoClient.getDatabase(DatabaseConfig.DATABASE_TITLE);
    }

    public static synchronized DrugStoreMongoConnector getInstance() {
        if(instance == null) {
            instance = new DrugStoreMongoConnector();
        }
        return instance;
    }
    public MongoCollection<Client> clients() {
        return database.getCollection(DatabaseConfig.CLIENTS_COLLECTION, Client.class);
    }
    public MongoCollection<Document> clientsDoc() {
        return database.getCollection(DatabaseConfig.CLIENTS_COLLECTION);
    }
    public MongoCollection<Order> orders() {
        return database.getCollection(DatabaseConfig.ORDERS_COLLECTION, Order.class);
    }
    public MongoCollection<Document> ordersDoc() {
        return database.getCollection(DatabaseConfig.ORDERS_COLLECTION);
    }
    public MongoCollection<Employee> employees() {
        return database.getCollection(DatabaseConfig.EMPLOYEES_COLLECTION, Employee.class);
    }
    public MongoCollection<Document> employeesDoc() {
        return database.getCollection(DatabaseConfig.EMPLOYEES_COLLECTION);
    }
    public MongoCollection<Medicine> medicines() {
        return database.getCollection(DatabaseConfig.MEDICINES_COLLECTION, Medicine.class);
    }
    public MongoCollection<Document> medicinesDoc() {
        return database.getCollection(DatabaseConfig.MEDICINES_COLLECTION);
    }
    public MongoCollection<Status> statuses() {
        return database.getCollection(DatabaseConfig.STATUSES_COLLECTION, Status.class);
    }
    public MongoCollection<Document> statusesDoc() {
        return database.getCollection(DatabaseConfig.STATUSES_COLLECTION);
    }
    public MongoCollection<Category> categories() {
        return database.getCollection(DatabaseConfig.CATEGORIES_COLLECTION, Category.class);
    }
    public MongoCollection<Document> categoriesDoc() {
        return database.getCollection(DatabaseConfig.CATEGORIES_COLLECTION);
    }
    public MongoCollection<Position> positions() {
        return database.getCollection(DatabaseConfig.POSITIONS_COLLECTION, Position.class);
    }
    public MongoCollection<Document> positionsDoc() {
        return database.getCollection(DatabaseConfig.POSITIONS_COLLECTION);
    }

    @Override
    public void close() {
        mongoClient.close();
    }
}
