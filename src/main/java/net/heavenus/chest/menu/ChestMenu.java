package net.heavenus.chest.menu;

import net.heavenus.chest.BasicChest;
import net.heavenus.chest.models.User;
import net.heavenus.chest.user.UserManager;
import net.heavenus.chest.utils.ItemSerializer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Objects;

public class ChestMenu implements Listener {

    protected User user;

    public ChestMenu(User user) {
        this.user = user;
    }

    public void open(Player player) {
        Bukkit.getServer().getPluginManager().registerEvents(this, BasicChest.getInstance());

        Inventory inventory = Bukkit.createInventory(null,
                BasicChest.getInstance().getConfig().getInt("menu.size"),
                BasicChest.getInstance().getConfig().getString("menu.title", "Chest: {player}").replace("{player}", user.getName()));

        user.inventory.keySet().forEach(key -> {
            inventory.setItem(Integer.parseInt(key), ItemSerializer.deserialize(user.inventory.get(key)));
        });
        player.openInventory(inventory);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        if (player.getName().equals(user.getName())) {
            HandlerList.unregisterAll(this);
        }
    }

    @EventHandler
    public void onClose(InventoryCloseEvent event) {

        if (!(event.getPlayer() instanceof Player player)) {
            return;
        }

        if (!player.getName().equals(user.getName())) {
            return;
        }

        HashMap<String, String> items = new HashMap<>();
        Inventory inventory = event.getInventory();
        for (int slot = 0; slot < BasicChest.getInstance().getConfig().getInt("menu.size"); slot++) {
            ItemStack item = inventory.getItem(slot);
            if (item != null) {
                items.put(String.valueOf(slot), ItemSerializer.serialize(item));
            }
        }

        user.setInventory(items);
        UserManager.replace(user);
        HandlerList.unregisterAll(this);
    }

}
