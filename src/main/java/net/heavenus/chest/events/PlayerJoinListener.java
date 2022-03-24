package net.heavenus.chest.events;

import net.heavenus.chest.BasicChest;
import net.heavenus.chest.models.User;
import net.heavenus.chest.user.UserManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {

    @EventHandler
    public void tryLoad(PlayerJoinEvent event){
        Player player = event.getPlayer();
        User user = UserManager.createOrLoad(player.getName());
        BasicChest.getInstance().getLogger().info("User: " + user.getName() + " loaded");
    }

}
