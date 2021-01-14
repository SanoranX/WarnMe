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
        if(plugin.database.isConnected()){
            if(strings[0].toLowerCase().equals("add")){
                if(commandSender.hasPermission("warnme.add")){
                    if(strings.length == 3) {  //Check if we have 3 arguments -> if no -> return false
                        if (!(commandSender instanceof ConsoleCommandSender) && plugin.databaseWorker.addWarn(getPlayer(strings[1]), (Player) commandSender, strings[2])) {
                            Bukkit.getConsoleSender().sendMessage(String.format(plugin.getConfig().getString("messages.console-warn-message"), strings[1], commandSender.getName(), strings[2]));
                            commandSender.sendMessage(plugin.getConfig().getString("messages.warn-success"));
                            commandSender.sendMessage(String.format(plugin.getConfig().getString("messages.after-warn-count"), strings[1], plugin.databaseWorker.checkWarnsCount(getPlayer(strings[1]))));
                        }
                        else if(commandSender instanceof ConsoleCommandSender){
                            if(plugin.databaseWorker.addWarn(getPlayer(strings[1]), strings[2])){
                                Bukkit.getConsoleSender().sendMessage(plugin.getConfig().getString("messages.warn-success"));
                                Bukkit.getConsoleSender().sendMessage(String.format(plugin.getConfig().getString("messages.after-warn-count"), strings[1], plugin.databaseWorker.checkWarnsCount(getPlayer(strings[1]))));
                            }
                        }
                        if(plugin.databaseWorker.checkWarnsCount(getPlayer(strings[1])) > 2){
                            if(getPlayer(strings[1]).isOnline()){
                                ((Player)getPlayer(strings[1])).kickPlayer((String.format(plugin.getConfig().getString("messages.player-warn-message"), commandSender.getName(), strings[2])) + "\n" + plugin.getConfig().getString("messages.player-warn-ban"));
                            }
                        }

                        if(getPlayer(strings[1]).isOnline()){
                            ((Player)getPlayer(strings[1])).sendMessage(String.format(plugin.getConfig().getString("messages.player-warn-message"), commandSender.getName(), strings[2]));
                        }
                        return true;
                    }
                    return false;
                }else{
                    commandSender.sendMessage(plugin.getConfig().getString("messages.not-enough-permission"));
                    return true;
                }
            } //END AFF "ADD" COMMAND

            else if(strings[0].toLowerCase().equals("clear") && strings.length == 2){
                if(commandSender.hasPermission("warnme.clear")){
                    Bukkit.getLogger().info("Clearing");
                    if(plugin.databaseWorker.cleanWarns(getPlayer(strings[1]))){
                        commandSender.sendMessage(String.format(plugin.getConfig().getString("messages.warn-clean"), strings[1]));
                        return true; //IF OPERATION SUCCEED -> RETURN TRUE
                    }
                    return false; // FALSE
                }else{
                    commandSender.sendMessage(plugin.getConfig().getString("messages.not-enough-permission"));
                    return true;
                }
            }

            else if(strings[0].toLowerCase().equals("info") && strings.length == 2){
                if(!commandSender.hasPermission("warnme.info.otherplayers") && !strings[1].equals(commandSender.getName())){
                    commandSender.sendMessage(plugin.getConfig().getString("messages.not-enough-permission"));
                    return true;
                }
                else{
                    Bukkit.getLogger().info("Checking if " + strings[1] + " has any warns by " + commandSender.getName());
                    commandSender.sendMessage(ChatColor.AQUA + "[Warn Me]" + ChatColor.WHITE);
                    commandSender.sendMessage(ChatColor.AQUA + "Player: " + ChatColor.WHITE + strings[1]);
                    commandSender.sendMessage(ChatColor.AQUA + "UUID: " + ChatColor.WHITE + getPlayer(strings[1]).getUniqueId());
                    commandSender.sendMessage(ChatColor.AQUA + "Warns: " + ChatColor.WHITE + plugin.databaseWorker.checkWarnsCount(getPlayer(strings[1])));
                    return true;
                }
            }
            return false;
        }
        commandSender.sendMessage(plugin.getConfig().getString("messages.database-connection-error")); //ONLY EXECUTES IF IT IS NOT CONNECTED
        return true; //TO PREVENT INFO
    }

    private OfflinePlayer getPlayer(String name){
        if(Bukkit.getOfflinePlayer(name).isOnline())
            return Bukkit.getPlayer(name);
        else
            return Bukkit.getOfflinePlayer(name);
    }
}