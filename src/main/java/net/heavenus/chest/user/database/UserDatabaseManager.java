package net.heavenus.chest.user.database;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import net.heavenus.chest.BasicChest;
import net.heavenus.chest.database.MongoDatabase;
import net.heavenus.chest.models.User;

import java.util.HashMap;
import java.util.UUID;

public class UserDatabaseManager {

    private static final MongoCollection<User> mongoCollection = BasicChest.getInstance().getMongoDatabase().getDatabase().getCollection("UserChestData", User.class);

    public static void replace(User value) {
        mongoCollection.replaceOne(Filters.eq("name", value.getName()), value);
    }

    public static User find(String name) {
        return mongoCollection.find(Filters.eq("name", name)).first();
    }

    public static User findOrCreate(String name) {
        User user = find(name);
        if (user == null) {
            user = new User(name, new HashMap<>());
            mongoCollection.insertOne(user);
        }
        return user;
    }

}
