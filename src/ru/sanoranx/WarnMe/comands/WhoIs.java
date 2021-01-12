package ru.sanoranx.WarnMe.comands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class WhoIs implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(commandSender instanceof Player){
            try{
                Player targetPlayer = Bukkit.getPlayer(strings[0]);
                ((Player)commandSender).sendMessage(getInfo(targetPlayer));
            }catch (IndexOutOfBoundsException e){
                ((Player)commandSender).sendMessage(ChatColor.AQUA + "[WarnMe]" + ChatColor.WHITE + " Вы должны указать игрока!");
                return false;
            }
            return true;
        }
        else if(commandSender instanceof ConsoleCommandSender){

        }
        return false;
    }

    private String getInfo(Player targetPlayer){
        return  (ChatColor.AQUA + "[WarnMe] Информация об игроке\n" +
                ChatColor.GOLD + "Ник: " + ChatColor.WHITE + targetPlayer.getName() + "\n" +
                ChatColor.GOLD + "IPv4: " + ChatColor.WHITE + targetPlayer.getAddress() + "\n" +
                ChatColor.GOLD + "Warns: " + ChatColor.WHITE + "0");
    }
}
