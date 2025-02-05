package javacode.helper;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.Properties;

public class MongoDBHelper {
    private final MongoClient mongoClient;
    private final MongoDatabase database;

    public MongoDBHelper(Properties properties) {
        this.mongoClient = MongoClients.create(properties.getProperty("mongoUri"));
        this.database = mongoClient.getDatabase(properties.getProperty("mongoDbName"));

    }

    public MongoCollection<Document> getCollection(String collectionName) {
        return database.getCollection(collectionName);
    }
    public void close() {
        mongoClient.close();
    }


}