package ru.sanoranx.WarnMe.database;

import ru.sanoranx.WarnMe.Main;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseWorker {
    private final Main plugin;

    public DatabaseWorker(Main plugin){
        this.plugin = plugin;
    }

    public int checkWarnsCount(OfflinePlayer player){
        try {
            PreparedStatement ps = plugin.database.getConnection().prepareStatement("SELECT COUNT(*) FROM warns where NICKNAME=?");
            ps.setString(1, player.getName());
            ResultSet results = ps.executeQuery();
            results.next();
            return results.getInt(1);
        }catch (SQLException e){
            Bukkit.getLogger().info(e.getMessage());
            Bukkit.getLogger().info("There was an error counting how many warns does "  + player.getName() + " have.");
            return 255;
        }
    }

    public boolean addWarn(OfflinePlayer targetPlayer, Player warner, String reason){
        try{
            PreparedStatement ps = plugin.database.getConnection().prepareStatement("INSERT warns(NICKNAME, NICKNAMEUID, GIVER, GIVERUID, REASON) " +
                    "VALUES (?, ?, ?, ?, ?)");
            ps.setString(1, targetPlayer.getName());
            ps.setString(2, targetPlayer.getUniqueId().toString());
            ps.setString(3, warner.getName());
            ps.setString(4, warner.getUniqueId().toString());
            ps.setString(5, reason);
            ps.execute();
            return true;
        }catch (SQLException e){
            Bukkit.getLogger().info("[/WARN]" + e.getMessage());
            e.printStackTrace();
            Bukkit.getLogger().info("There was an error when giving a warn");
            return false;
        }
    }

    public boolean addWarn(OfflinePlayer targetPlayer, String reason){
        try{
            PreparedStatement ps = plugin.database.getConnection().prepareStatement("INSERT warns(NICKNAME, NICKNAMEUID, GIVER, GIVERUID, REASON) " +
                    "VALUES (?, ?, ?, ?, ?)");
            ps.setString(1, targetPlayer.getName());
            ps.setString(2, targetPlayer.getUniqueId().toString());
            ps.setString(3, "CONSOLE");
            ps.setString(4, "CONSOLE:");
            ps.setString(5, reason);
            ps.execute();
            return true;
        }catch (SQLException e){
            Bukkit.getLogger().info("[/WARN]" + e.getMessage());
            e.printStackTrace();
            Bukkit.getLogger().info("There was an error when giving a warn");
            return false;
        }
    }

    public boolean cleanWarns(OfflinePlayer targetPlayer){
        try{
            PreparedStatement ps = plugin.database.getConnection().prepareStatement("DELETE FROM warns WHERE NICKNAME=?");
            ps.setString(1, targetPlayer.getName());
            ps.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
