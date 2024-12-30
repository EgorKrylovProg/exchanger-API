package DAO;

import org.sqlite.SQLiteConfig;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataBaseConnection {

    private static final String PROTOCOL = "jdbc:sqlite:/Users/egorkrylov/IdeaProjects/exchanger-API/src/main/resources/exchangeRate.db";

    public Connection getConnection() {

        try {
            Class.forName("org.sqlite.JDBC");
            SQLiteConfig config = new SQLiteConfig();
            config.enforceForeignKeys(true);
            return DriverManager.getConnection(PROTOCOL, config.toProperties());
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
