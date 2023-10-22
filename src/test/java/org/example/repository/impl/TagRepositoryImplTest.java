package org.example.repository.impl;



import org.example.db.ConnectionManager;
import org.example.model.PostEntity;
import org.example.model.TagEntity;
import org.example.repository.TagRepository;
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
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@Testcontainers
public class TagRepositoryImplTest {
    @Mock
    private ConnectionManager connectionManager;

    private TagRepository tagRepository;

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
        tagRepository = new TagRepositoryImpl(connectionManager);
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
    void testFindTagsByPostId() {
        List<TagEntity> expectedTags = Arrays.asList(new TagEntity(1L, "Tag 1"), new TagEntity(2L, "Tag 2"));
        PostEntity postEntity = new PostEntity(1L, "Post 1", "Content for post 1", 1L, expectedTags);

       List<TagEntity> actualTagsEntity = tagRepository.findTagsByPostId(postEntity);

        assertEquals(expectedTags, actualTagsEntity);

    }

    @Test
    void testAddTagToPost() {
        List<TagEntity> expectedTags = Arrays.asList(new TagEntity(2L, "Tag 2"), new TagEntity(3L, "Tag 3"));
        List<TagEntity> tags = List.of(new TagEntity(3L, "Tag 3"));

        PostEntity post = new PostEntity(2L, "Post 2", "Content for post 2", 1L, tags);

        tagRepository.addTagToPost(post);

        List<TagEntity> actualTags = tagRepository.findTagsByPostId(post);

        assertEquals(expectedTags, actualTags);

    }

    @Test
    void testRemoveTagFromPost() {
        List<TagEntity> tags = Arrays.asList(new TagEntity(2L, "Tag 2"), new TagEntity(3L, "Tag 3"));
        TagEntity tagEntity = new TagEntity(3L, "Tag 3");
        List<TagEntity> expectedTags = List.of(tagEntity);

        PostEntity post = new PostEntity(2L, "Post 2", "Content for post 2", 1L, tags);

        tagRepository.removeTagFromPost(post);

        List<TagEntity> actualTags = tagRepository.findTagsByPostId(post);

        assertEquals(expectedTags,actualTags);
    }
    @Test
    void testUpdateTagsForPost() {
        List<TagEntity> expectedTags = List.of(new TagEntity(1L, "Tag 1"));
        PostEntity post = new PostEntity(3L, "Post 3", "Content for post 3", 2, expectedTags);

        tagRepository.updateTagsForPost(post);

        List<TagEntity> actualTags = tagRepository.findTagsByPostId(post);

        assertEquals(expectedTags, actualTags);
    }
}
