package org.example;

import com.zaxxer.hikari.HikariDataSource;
import org.example.db.MySQLConnection;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.sql.Connection;
import java.sql.SQLException;

class MySQLConnectionTest {

    @InjectMocks
    private MySQLConnection mySQLConnection;
    @Mock
    private HikariDataSource dataSource;
    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        mySQLConnection = new MySQLConnection(dataSource);
    }
    @Test
    void testGetConnection() throws SQLException {
        Connection mockConnection = Mockito.mock(Connection.class);
        Mockito.when(dataSource.getConnection()).thenReturn(mockConnection);
        Connection connection = mySQLConnection.getConnection();
        Assertions.assertEquals(mockConnection, connection);
    }

}
