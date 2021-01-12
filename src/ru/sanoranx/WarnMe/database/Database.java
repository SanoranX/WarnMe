package ru.sanoranx.WarnMe.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Database {
    private final String host = "localhost";
    private final String port = "3306";
    private final String database = "minecraft";
    private final String username = "sanoranx";
    private final String password = "password";

    private Connection connection;

    public boolean isConnected() {
        return (connection != null);
    }

    public void connect() throws ClassNotFoundException, SQLException {
        if (!isConnected()) {
            connection = DriverManager.getConnection("jdbc:mysql://" +
                            host + ":" + port + "/" + database + "?useSSL=false" + "&allowPublicKeyRetrieval=true",
                    username, password);
        }
        PreparedStatement statement = connection.prepareStatement(
                "CREATE TABLE IF NOT EXISTS `warns` (" +
                        "`NICKNAME` VARCHAR(100) NOT NULL," +
                        "`NICKNAMEUID` VARCHAR(100) NOT NULL," +
                        "`GIVER` VARCHAR(100) NOT NULL," +
                        "`GIVERUID` VARCHAR(100) NOT NULL," +
                        "`REASON` VARCHAR(100) NOT NULL)");
        statement.execute();
    }

    public void close() {
        if (isConnected()) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public Connection getConnection() {
        return connection;
    }
}
