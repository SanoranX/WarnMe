package ru.sanoranx.WarnMe.comands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import ru.sanoranx.WarnMe.Main;

public class Info implements CommandExecutor {

    private Main plugin;

    public Info(Main plugin){
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(commandSender instanceof Player){
            Player player = (Player)commandSender;
            player.sendMessage(ChatColor.AQUA + "Player has: " + ChatColor.WHITE + plugin.databaseWorker.checkWarnsCount(Bukkit.getPlayer(strings[0])));
        }
        return false;
    }
}
