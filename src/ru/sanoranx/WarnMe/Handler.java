package ru.sanoranx.WarnMe;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import java.net.URISyntaxException;

public class Handler implements Listener {

    private Main plugin;

    public Handler(Main plugin){
        this.plugin = plugin;
    }

    @EventHandler
    public void join(PlayerJoinEvent e) throws URISyntaxException {
        Player player = e.getPlayer();
        Bukkit.getLogger().info("Checking player " + player.getName() + " for warns");
        if(plugin.databaseWorker.checkWarnsCount(player) > 2){
            player.kickPlayer(ChatColor.RED + "[Warn Me] Your account has been blocked because of warns.");
        }
    }

}
