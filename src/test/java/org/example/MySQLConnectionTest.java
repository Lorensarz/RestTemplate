package org.example;

import com.zaxxer.hikari.HikariDataSource;
import org.example.db.MySQLConnection;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class MySQLConnectionTest {

    @Mock
    private HikariDataSource dataSource;
    private MySQLConnection mySQLConnection;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        MySQLConnection.setDataSource(dataSource);
        mySQLConnection = new MySQLConnection();
    }



    @Test
    void testGetConnection() throws SQLException {
        Connection mockConnection = mock(Connection.class);
        when(dataSource.getConnection()).thenReturn(mockConnection);
        Connection connection = mySQLConnection.getConnection();
        assertEquals(mockConnection, connection);
    }

}
