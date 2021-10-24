package me.zhichgaming.flyplugin.commands;

import me.zhichgaming.flyplugin.FlyPlugin;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.*;
import org.bukkit.entity.Player;

import java.util.ArrayList;

import static me.zhichgaming.flyplugin.SendMessage.printToConsole;
import static me.zhichgaming.flyplugin.SendMessage.sendToPlayer;

public class FlyCommand implements CommandExecutor {

    private FlyPlugin plugin;
    private ArrayList<Player> flyList = new ArrayList<>();

    public FlyCommand(FlyPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player) {

            Player player = (Player) sender;

            if (args.length == 0) {

                if (player.hasPermission("flyplugin.fly")) {
                    flyMethod(player);

                } else {
                    player.sendMessage("You do not have the permission to do /fly");
                }

            } else if (args.length == 1) {

                Player target = Bukkit.getPlayer(args[0]);

                if (args[0].equals("list")) {

                    if (player.hasPermission("flyplugin.list")) {
                        sendToPlayer(player, listMethod());
                    }

                } else if (!(target == null)) {

                    if (player.hasPermission("flyplugin.fly.others")) {
                        flyMethod(target);
                        sendToPlayer(player, target.getDisplayName() + " now has fly.");

                    } else {
                        player.sendMessage("You do not have the permission to make others fly.");
                    }

                } else if (target == null) {
                    sendToPlayer(player, "This player is not online.");
                }
            }

        } else if (sender instanceof ConsoleCommandSender) {

            if (args.length == 0) {
                printToConsole("This command can only be run by a player.");

            } else if (args.length == 1) {

                Player target = Bukkit.getPlayer(args[0]);

                if (args[0].equals("list")) {
                    printToConsole(listMethod());

                } else if (!(target == null)) {
                    flyMethod(target);
                    printToConsole(target.getDisplayName() + " now has fly.");

                } else if (target == null) {
                    printToConsole("This player is not online.");
                }
            }
        } else {
            printToConsole("This command can only be run by a player.");
        }

        return true;
    }



    private void flyMethod(Player player) {

        if (flyList.contains(player)) {
            player.setAllowFlight(false);
            sendToPlayer(player, ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("off-message")));
            flyList.remove(player);

        } else if (!flyList.contains(player)) {
            player.setAllowFlight(true);
            sendToPlayer(player, ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("on-message")));
            flyList.add(player);
        }
    }

    private String listMethod() {

        String flyingPlayers = "";
        if (!(flyList.isEmpty())) {

            for (Player player : flyList) {
                if (flyingPlayers.length() >= 1) {
                    flyingPlayers += ", ";
                }

                flyingPlayers += player.getName();
            }

        } else if (flyList.isEmpty()) {
            flyingPlayers = "There are no flying players.";
        }

        return "List of flying players: " + flyingPlayers;
    }
}