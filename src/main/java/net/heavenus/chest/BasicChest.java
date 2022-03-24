package net.heavenus.chest;

import net.heavenus.chest.commands.ChestCommand;
import net.heavenus.chest.database.MongoDatabase;
import net.heavenus.chest.events.PlayerJoinListener;
import net.heavenus.chest.events.PlayerQuitListener;
import org.bukkit.plugin.java.JavaPlugin;

public final class BasicChest extends JavaPlugin {

    private MongoDatabase mongoDatabase;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        this.mongoDatabase = new MongoDatabase(
                getConfig().getString("mongodb.uri"),
                getConfig().getString("mongodb.database"));

        if (this.mongoDatabase.connect()) {
            getServer().getPluginManager().registerEvents(new PlayerJoinListener(), this);
            getServer().getPluginManager().registerEvents(new PlayerQuitListener(), this);
            getCommand("chest").setExecutor(new ChestCommand());
            getLogger().info("The plugin has been enabled successfully.");
        } else {
            getLogger().severe("The plugin has been disabled because of an error.");
            getLogger().severe("Details: The Database hasn't been configured or the connection has failed.");
            this.setEnabled(false);
        }
    }

    @Override
    public void onDisable() {
        getLogger().info("The plugin has been disabled successfully.");
    }

    public static BasicChest getInstance() {
        return getPlugin(BasicChest.class);
    }

    public MongoDatabase getMongoDatabase() {
        return mongoDatabase;
    }
}
