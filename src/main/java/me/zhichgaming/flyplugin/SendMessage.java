package me.zhichgaming.flyplugin;

import org.bukkit.ChatColor;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import static org.bukkit.Bukkit.getServer;

public class SendMessage {
    public static String prefix = ChatColor.GRAY + "[" + ChatColor.DARK_AQUA + "FlyPlugin" + ChatColor.GRAY + "] " + ChatColor.RESET;

    public static void printToConsole(Object message) {
        getServer().getConsoleSender().sendMessage(prefix + message);
    }

    public static void sendToPlayer(Player player, String message) {
        player.sendMessage(prefix + message);
    }
}
