package net.heavenus.chest.database;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import net.heavenus.chest.models.User;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.bson.UuidRepresentation;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.ClassModel;
import org.bson.codecs.pojo.PojoCodecProvider;


public class MongoDatabase {

    /*
    Constructor fields, class initialization
     */

    protected String uri;
    protected String databaseName;

    /*
    Database fields
     */
    protected com.mongodb.client.MongoDatabase database;
    protected MongoClient client;

    /*
    Other
     */

    protected CodecRegistry codecRegistry;

    public MongoDatabase(String uri, String databaseName) {
        this.uri = uri;
        this.databaseName = databaseName;
    }

    public boolean connect() {
        ClassModel<User> userClassModel = ClassModel.builder(User.class).enableDiscriminator(true).build();
        CodecRegistry pojoCodecRegistry = CodecRegistries.fromRegistries(
                MongoClientSettings.getDefaultCodecRegistry(),
                CodecRegistries.fromProviders(PojoCodecProvider.builder().register(userClassModel).build()));
        this.codecRegistry = pojoCodecRegistry;

        ConnectionString connectionString = new ConnectionString(uri);
        MongoClientSettings clientSettings = MongoClientSettings.builder().uuidRepresentation(UuidRepresentation.JAVA_LEGACY)
                .applyConnectionString(connectionString).codecRegistry(pojoCodecRegistry).build();

        this.client = MongoClients.create(clientSettings);
        this.database = this.client.getDatabase(databaseName);
        if(!collectionExists("UserChestData")) database.createCollection("UserChestData");

        return client != null;
    }


    public String getUri() {
        return uri;
    }

    public String getDatabaseName() {
        return databaseName;
    }

    public com.mongodb.client.MongoDatabase getDatabase() {
        return database;
    }

    public MongoClient getClient() {
        return client;
    }

    /*
    Other methods
     */

    public boolean collectionExists(String collectionName) {
        for(String name : this.database.listCollectionNames()) {
            if(name.equals(collectionName)) {
                return true;
            }
        }
        return false;
    }

    public <T> MongoCollection<T> getCollection(String collectionName, Class<T> clazz) {
        return this.database.getCollection(collectionName, clazz);
    }

    public boolean isConnected() {
        return this.client != null;
    }
}
