package org.example.db;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class MySQLConnection implements ConnectionManager {
    private static final HikariConfig config = new HikariConfig();
    private static HikariDataSource dataSource;

    static {
        config.setJdbcUrl("jdbc:mysql://localhost:3306/rest_template");
        config.setUsername("root");
        config.setPassword("root");
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        dataSource = new HikariDataSource(config);
    }

    public MySQLConnection() {
    }

    public static void setDataSource(HikariDataSource dataSource) {
        MySQLConnection.dataSource = dataSource;
    }

    @Override
    public Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }
}
