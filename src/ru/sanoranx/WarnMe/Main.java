package ru.sanoranx.WarnMe;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;
import ru.sanoranx.WarnMe.comands.Info;
import ru.sanoranx.WarnMe.comands.Warn;
import ru.sanoranx.WarnMe.database.Database;
import ru.sanoranx.WarnMe.database.DatabaseWorker;

import java.sql.SQLException;

public class Main extends JavaPlugin {

    public Database database;
    public DatabaseWorker databaseWorker;

    @Override
    public void onEnable(){
        this.database = new Database(this.getConfig().getString("db.host"), this.getConfig().getString("db.port"), this.getConfig().getString("db.database"), getConfig().getString("db.user"), getConfig().getString("db.password"));
        this.databaseWorker = new DatabaseWorker(this);
        try {
            database.connect();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwable) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "[DATABASE CONNECTION ERROR] " + throwable.getMessage());
            getLogger().info( "Database is not connected");
        }

        if(database.isConnected()){
            getLogger().info("Database connected");
        }

        Bukkit.getPluginManager().registerEvents(new Handler(this), this);
        getCommand("info").setExecutor(new Info(this));
        getCommand("warn").setExecutor(new Warn(this));
        getLogger().info("Enabled");
        getLogger().info("Warn system v0.1 activated");
        getLogger().info("Made by SanoranX");
        getLogger().info(ChatColor.AQUA + "|" + ChatColor.WHITE);
    }


    @Override
    public void onDisable(){
        getLogger().info("Disabled");
    }
}
