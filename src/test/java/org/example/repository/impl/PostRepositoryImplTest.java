package org.example.repository.impl;

import org.example.db.ConnectionManager;
import org.example.model.PostEntity;
import org.example.model.TagEntity;
import org.example.repository.PostRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@Testcontainers
public class PostRepositoryImplTest {

    @Mock
    private ConnectionManager connectionManager;

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
        postRepository = new PostRepositoryImpl(connectionManager);
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
    void testFindPostsByUserId() {
        long userId = 1L;
//       (1, 'Post 1', 'Content for post 1', 1),
        List<TagEntity> tagEntities = Arrays.asList(new TagEntity(1L, "Tag 1"),
                new TagEntity(2L, "Tag 2"));

        PostEntity expectedPost = new PostEntity(1, "Post 1",
                "Content for post 1", 1L, tagEntities);


        List<PostEntity> expectedPostEntities = new ArrayList<>();
        expectedPostEntities.add(expectedPost);

        List<PostEntity> posts = postRepository.findPostsByUserId(userId);

        assertEquals(expectedPostEntities, posts);
        System.out.println(expectedPostEntities + " - " + posts);
    }

    @Test
    void testFindAll() {
        PostRepository postRepository = new PostRepositoryImpl(connectionManager);

        List<PostEntity> expectedPosts = Arrays.asList(
                new PostEntity(1, "Post 1", "Content for post 1", 1, Arrays.asList(
                        new TagEntity(1, "Tag 1"),
                        new TagEntity(2, "Tag 2"))),
                new PostEntity(2, "Post 2", "Content for post 2", 2, Arrays.asList(
                        new TagEntity(2, "Tag 2")))
        );

        List<PostEntity> actualPosts = postRepository.findAll();

        assertEquals(expectedPosts, actualPosts);
    }


    @Test
    void testSave() {
        // Your test logic here
    }

    @Test
    void testUpdate() {
        // Your test logic here
    }

    @Test
    void testFindPostsByTag() {
        // Your test logic here
    }

    @Test
    void testDelete() {
        // Your test logic here
    }
}
