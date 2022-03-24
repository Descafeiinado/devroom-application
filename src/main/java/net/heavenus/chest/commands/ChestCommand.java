package net.heavenus.chest.commands;

import net.heavenus.chest.BasicChest;
import net.heavenus.chest.menu.ChestMenu;
import net.heavenus.chest.user.UserManager;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ChestCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("§cYou must be a Player in order to use this command.");
            return false;
        }

        if (!player.hasPermission("basicchests.cmd.chest")) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', BasicChest.getInstance().getConfig().getString("language.no-permission")));
        }
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', BasicChest.getInstance().getConfig().getString("language.opening")));
        new ChestMenu(UserManager.createOrLoad(player.getName())).open(player);
        return true;
    }
}
