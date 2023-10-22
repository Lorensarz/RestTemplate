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
import static org.junit.jupiter.api.Assertions.assertNull;
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
        List<TagEntity> tagEntities = Arrays.asList(new TagEntity(1L, "Tag 1"),
                new TagEntity(2L, "Tag 2"));
        List<TagEntity> tagEntities2 = List.of(new TagEntity(2L, "Tag 2"));


        PostEntity expectedPost = new PostEntity(1, "Post 1",
                "Content for post 1", 1L, tagEntities);
        PostEntity expectedPost2 = new PostEntity(2, "Post 2",
                "Content for post 2", 1L, tagEntities2);

        List<PostEntity> expectedPostEntities = new ArrayList<>();
        expectedPostEntities.add(expectedPost);
        expectedPostEntities.add(expectedPost2);

        List<PostEntity> posts = postRepository.findPostsByUserId(userId);

        assertEquals(expectedPostEntities, posts);
        System.out.println(expectedPostEntities + " \n " + posts);
    }
    @Test
    void testFindPostByPostId() {
        List<TagEntity> tags = List.of(new TagEntity(1L, "Tag 1"), new TagEntity(2L, "Tag 2"));
        PostEntity expectedPostEntity = new PostEntity(1L, "Post 1", "Content for post 1", 1L, tags);

        PostEntity actualPostEntity = postRepository.findPostByPostId(1L);

        assertEquals(expectedPostEntity, actualPostEntity);

    }

    @Test
    void testFindAll() {

        TagEntity tagEntity1 = new TagEntity(1, "Tag 1");
        TagEntity tagEntity2 = new TagEntity(2, "Tag 2");
        TagEntity tagEntity3 = new TagEntity(3, "Tag 3");

        List<TagEntity> listTags1 = Arrays.asList(tagEntity1, tagEntity2);
        List<TagEntity> listTags2 = List.of(tagEntity2);
        List<TagEntity> listTags3 = List.of(tagEntity3);

        PostEntity postEntity1 = new PostEntity(1, "Post 1", "Content for post 1", 1, listTags1);
        PostEntity postEntity2 = new PostEntity(2, "Post 2", "Content for post 2", 1, listTags2);
        PostEntity postEntity3 = new PostEntity(3, "Post 3", "Content for post 3", 2, listTags3);


        List<PostEntity> expectedPosts = Arrays.asList(postEntity1, postEntity2, postEntity3);
        List<PostEntity> actualPosts = postRepository.findAll();

        assertEquals(expectedPosts, actualPosts);
    }


    @Test
    void testSave() {
        List<TagEntity> listTags = List.of(new TagEntity(3, "Tag 3"));
        List<TagEntity> listTags2 = List.of(new TagEntity(2, "Tag 2"));


        PostEntity postEntity2 = new PostEntity(3, "Post 3", "Content for post 3", 2, listTags);
        PostEntity postEntity4 = new PostEntity(4, "Post 4", "Content for post 4", 2, listTags2);

        List<PostEntity> expectedPosts = Arrays.asList(postEntity2, postEntity4);

        postRepository.save(postEntity4);

        List<PostEntity> actualPosts = postRepository.findPostsByUserId(2L);

        assertEquals(expectedPosts, actualPosts);

    }

    @Test
    void testUpdate() {
        List<TagEntity> tags = List.of(new TagEntity(2L, "Tag 2"));

        PostEntity expectedUpdatedPost = new PostEntity(2L, "Updated Title", "Updated Content", 2L, tags);

        postRepository.update(expectedUpdatedPost);

        PostEntity actualPosts = postRepository.findPostByPostId(expectedUpdatedPost.getId());
        assertEquals(expectedUpdatedPost, actualPosts);

    }

    @Test
    void testFindPostsByTag() {
        List<TagEntity> tags = List.of(new TagEntity(3L, "Tag 3"));
        List<PostEntity> expectedPostEntities = List.of(
                new PostEntity(3L, "Post 3", "Content for post 3", 2L, tags));

        List<PostEntity> actualPostsByTag = postRepository.findPostsByTag(tags.get(0));

        assertEquals(expectedPostEntities, actualPostsByTag);

    }

    @Test
    void testDelete() {
        List<TagEntity> tags = List.of(new TagEntity(3, "Tag 3"));
        PostEntity post = new PostEntity(3L, "Post 3", "Content for post 3", 2, tags);

        postRepository.delete(post.getId());

        assertNull(postRepository.findPostByPostId(post.getId()));
    }
}
