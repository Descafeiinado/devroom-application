package net.heavenus.chest.user;

import com.google.common.collect.Lists;
import net.heavenus.chest.models.User;
import net.heavenus.chest.user.database.UserDatabaseManager;

import java.util.Collections;
import java.util.HashMap;
import java.util.UUID;

public class UserManager {
    public static HashMap<String, User> USERS = new HashMap<>();

    public static User createOrLoad(String name) {
        User user = USERS.get(name);
        if (user == null) {
            user = UserDatabaseManager.findOrCreate(name);
            USERS.put(name, user);
        }
        return user;
    }

    public static void remove(String name) {
        User user = USERS.get(name);
        UserDatabaseManager.replace(user);
        USERS.remove(name);
    }

    public static void replace(User user) {
        USERS.put(user.getName(), user);
    }

}
