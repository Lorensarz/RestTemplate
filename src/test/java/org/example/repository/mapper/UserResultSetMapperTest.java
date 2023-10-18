package org.example.repository.mapper;

import org.example.model.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserResultSetMapperTest {

    private UserResultSetMapper userResultSetMapper;
    @Mock
    private ResultSet resultSet;
    @BeforeEach
    void setUp() {
        userResultSetMapper = new UserResultSetMapperImpl();
    }

    @Test
    void testMap() throws SQLException {
        when(resultSet.getLong("user_id")).thenReturn(1L);
        when(resultSet.getString("user_name")).thenReturn("John");
        when(resultSet.getString("email")).thenReturn("john@example.com");

        UserEntity user = userResultSetMapper.map(resultSet);

        assertEquals(1L, user.getId());
        assertEquals("John", user.getName());
        assertEquals("john@example.com", user.getEmail());
    }
}
