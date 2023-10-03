package org.example.db;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class MySQLConnection implements ConnectionManager {
    private static HikariDataSource dataSource;

    public MySQLConnection(HikariDataSource dataSource) {
        this.dataSource = dataSource;
    }


    static {
        HikariConfig config = new HikariConfig();

        try (InputStream is = MySQLConnection.class.getClassLoader().getResourceAsStream("db.properties")) {
            if (is == null) {
                throw new RuntimeException("Не удалось найти файл db.properties");
            }

            Properties properties = new Properties();
            properties.load(is);

            config.setJdbcUrl(properties.getProperty("db.url"));
            config.setUsername(properties.getProperty("db.username"));
            config.setPassword(properties.getProperty("db.password"));
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Ошибка при загрузке настроек из db.properties", e);
        }

        config.setMaximumPoolSize(10);

        dataSource = new HikariDataSource(config);
    }

    @Override
    public Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    public void close() {
        dataSource.close();
    }
}
