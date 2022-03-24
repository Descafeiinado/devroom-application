package net.heavenus.chest.events;

import net.heavenus.chest.BasicChest;
import net.heavenus.chest.models.User;
import net.heavenus.chest.user.UserManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuitListener implements Listener {

    @EventHandler
    public void tryUnload(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        UserManager.remove(player.getName());
        BasicChest.getInstance().getLogger().info("User: " + player.getName()+ " unloaded");
    }

}
