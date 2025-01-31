package DAO;

import org.apache.commons.dbcp2.BasicDataSource;

import java.util.List;

public class ConnectionPoolSqlite {

    private final String url = "jdbc:sqlite:/Users/egorkrylov/IdeaProjects/exchanger-API/src/main/resources/exchangeRate.db";

    public ConnectionPoolSqlite() {
        loadingDriver();
    }

    public BasicDataSource getBaseDataSource() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setUrl(url);

        dataSource.setMinIdle(5);
        dataSource.setMaxIdle(10);
        dataSource.setMaxTotal(10);
        dataSource.setConnectionInitSqls(List.of("PRAGMA foreign_keys = ON"));
        return dataSource;
    }

    private void loadingDriver() {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

}
