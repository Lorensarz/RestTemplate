package org.example.repository.impl;

import org.example.db.ConnectionManager;
import org.example.model.UserEntity;
import org.example.repository.PostRepository;
import org.example.repository.UserRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@Testcontainers
public class UserRepositoryImplTest {

    @Mock
    private ConnectionManager connectionManager;

    private UserRepository userRepository;
    private PostRepository postRepository;

    @Container
    public static MySQLContainer<?> mysqlContainer = new MySQLContainer<>("mysql:latest")
            .withDatabaseName("testdb")
            .withUsername("testuser")
            .withPassword("testpass")
            .withInitScript("init_db.sql");

    private static Connection containerConnection;

    @BeforeAll
    static void beforeAll() throws SQLException {
        mysqlContainer.start();
        String jdbcUrl = mysqlContainer.getJdbcUrl();
        String username = mysqlContainer.getUsername();
        String password = mysqlContainer.getPassword();
        System.setProperty("DB_URL", jdbcUrl);
        System.setProperty("DB_USER", username);
        System.setProperty("DB_PASSWORD", password);
        System.setProperty("DB_INIT_SCRIPT", "init_db.sql");
        containerConnection = DriverManager.getConnection(jdbcUrl, username, password);

    }

    @BeforeEach
    void setUp() throws SQLException {
        userRepository = new UserRepositoryImpl(connectionManager);
        containerConnection = DriverManager.getConnection(
                mysqlContainer.getJdbcUrl(),
                mysqlContainer.getUsername(),
                mysqlContainer.getPassword());
        when(connectionManager.getConnection()).thenReturn(containerConnection);
    }

    @AfterEach
    void tearDown() throws SQLException {
        containerConnection.close();
    }


    @Test
    void testFindById() throws SQLException {
        // Arrange
        long userId = 1;
        UserEntity expectedUser = new UserEntity();
        expectedUser.setId(1L);
        expectedUser.setName("User 1");
        expectedUser.setEmail("user1@example.com");

        // Act
        UserEntity user = userRepository.findById(userId);

        // Assert
        assertNotNull(user);
        assertEquals(expectedUser, user);
    }

    @Test
    void testFindAll() throws SQLException {
        // Arrange
        List<UserEntity> expectedUsers = new ArrayList<>();

        UserEntity expectedUser1 = new UserEntity();
        expectedUser1.setId(1L);
        expectedUser1.setName("User 1");
        expectedUser1.setEmail("user1@example.com");

        UserEntity expectedUser2 = new UserEntity();
        expectedUser2.setId(2L);
        expectedUser2.setName("User 2");
        expectedUser2.setEmail("user2@example.com");

        expectedUsers.add(expectedUser1);
        expectedUsers.add(expectedUser2);

        // Act
        List<UserEntity> users = userRepository.findAll();

        // Assert
        Assertions.assertEquals(expectedUsers, users);

    }

    @Test
    void testSave() throws SQLException {
        // Arrange
        UserEntity newUser = new UserEntity();
        newUser.setName("New User");
        newUser.setEmail("newuser3@example.com");

        // Act
        boolean result = userRepository.save(newUser);

        // Assert
        assertTrue(result);
    }

    @Test
    void testUpdate() throws SQLException {
        // Arrange
        UserEntity updatedUser = new UserEntity();
        updatedUser.setName("Updated User");
        updatedUser.setEmail("updateduser@example.com");
        updatedUser.setId(1L);

        // Act
        userRepository.update(updatedUser);

        setUp();

        // Assert
        UserEntity expectedEntity = userRepository.findById(1L);
        assertEquals(expectedEntity, updatedUser);
        System.out.println(expectedEntity + " - " + updatedUser);

    }

    @Test
    void testDelete() {
        long userId = 1;
        boolean expectedDelete = userRepository.deleteById(userId);
        assertTrue(expectedDelete);
    }
}
