package ru.sanoranx.WarnMe.comands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import ru.sanoranx.WarnMe.Main;

public class Warn implements CommandExecutor {

    private Main plugin;

    public Warn(Main main) {
        this.plugin = main;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(strings[0].toLowerCase().equals("add")){
            if(strings.length == 3) {
                boolean isOk = false;
                if (!(commandSender instanceof ConsoleCommandSender) && plugin.databaseWorker.addWarn(getPlayer(strings[1]), (Player) commandSender, strings[2])) {
                    Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "Player: " + strings[0] + " was warned with reason of: " + strings[2] + " by: " + commandSender.getName());
                    System.out.println("Message:" + plugin.getConfig().getString("messages.welcome"));
                    commandSender.sendMessage(plugin.getConfig().getString("messages.welcome"));
                    commandSender.sendMessage(ChatColor.AQUA + "[Warn Me]" + ChatColor.WHITE + " Player was successfully warned.");
                    commandSender.sendMessage(ChatColor.AQUA + "[Warn Me]" + ChatColor.WHITE + " Player " + strings[1] + " now has: " + plugin.databaseWorker.checkWarnsCount(getPlayer(strings[1])) + " warns");
                    isOk = true;
                }
                else if(commandSender instanceof ConsoleCommandSender){
                    if(plugin.databaseWorker.addWarn(getPlayer(strings[1]), strings[2])){
                        Bukkit.getConsoleSender().sendMessage(ChatColor.AQUA + "[Warn Me]" + ChatColor.WHITE + " Player was successfully warned.");
                        Bukkit.getConsoleSender().sendMessage(ChatColor.AQUA + "[Warn Me]" + ChatColor.WHITE + " Player " + strings[1] + " now has: " + plugin.databaseWorker.checkWarnsCount(getPlayer(strings[1])) + " warns");
                    }
                }

                if(plugin.databaseWorker.checkWarnsCount(getPlayer(strings[1])) > 2){
                    if(getPlayer(strings[1]).isOnline()){
                        ((Player)getPlayer(strings[1])).kickPlayer(ChatColor.AQUA + "[Warn Me] " + ChatColor.WHITE + commandSender.getName() + " has just warned you with reason of [" + strings[2] + "]. " + ChatColor.RED + "\nYou have been banned because of 3 warns");
                    }
                }

                if(getPlayer(strings[1]).isOnline()){
                    ((Player)getPlayer(strings[1])).sendMessage(ChatColor.AQUA + "[Warn Me] " + ChatColor.WHITE + commandSender.getName() + " has just warned you with reason of [" + strings[2] + "]. " + "You now have: " + plugin.databaseWorker.checkWarnsCount(getPlayer(strings[1])) + " warns");
                }
                return true;
            }
            return false;
        }
        else if(strings[0].toLowerCase().equals("clear")){
            Bukkit.getLogger().info("Clearing");
            if(plugin.databaseWorker.cleanWarns(getPlayer(strings[1]))){
                commandSender.sendMessage(ChatColor.AQUA + "[Warn Me] " + strings[1] + ChatColor.WHITE + " Has been cleaned.");
            }
            return true;
        }
        else if(strings[0].toLowerCase().equals("info")){
            Bukkit.getLogger().info("Checking if " + strings[1] + " has any warns by " + commandSender.getName());
            commandSender.sendMessage(ChatColor.AQUA + "[Warn Me]" + ChatColor.WHITE);
            commandSender.sendMessage(ChatColor.AQUA + "Player: " + ChatColor.WHITE + strings[1]);
            commandSender.sendMessage(ChatColor.AQUA + "UUID: " + ChatColor.WHITE + getPlayer(strings[1]).getUniqueId());
            commandSender.sendMessage(ChatColor.AQUA + "Warns: " + ChatColor.WHITE + plugin.databaseWorker.checkWarnsCount(getPlayer(strings[1])));
            return true;
        }
        return false;

    }

    private OfflinePlayer getPlayer(String name){
        if(Bukkit.getOfflinePlayer(name).isOnline())
            return Bukkit.getPlayer(name);
        else
            return Bukkit.getOfflinePlayer(name);
    }
}