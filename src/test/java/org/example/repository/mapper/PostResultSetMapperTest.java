package org.example.repository.mapper;

import org.example.model.PostEntity;
import org.example.model.TagEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PostResultSetMapperTest {
    private PostResultSetMapper postResultSetMapper;

    @Mock
    private ResultSet resultSet;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        postResultSetMapper = new PostResultSetMapperImpl();
    }

    @Test
    void testMapWithTags() throws SQLException {
        // Arrange
        Mockito.when(resultSet.getLong("post_id")).thenReturn(1L);
        Mockito.when(resultSet.getString("content")).thenReturn("Content 1");
        Mockito.when(resultSet.getString("title")).thenReturn("Title 1");
        Mockito.when(resultSet.getLong("user_id")).thenReturn(1L);

        // Моделируем наличие тегов в ResultSet
        Mockito.when(resultSet.getLong("tag_id")).thenReturn(1L);
        Mockito.when(resultSet.getString("tag_name")).thenReturn("Tag 1");

        // Act
        PostEntity post = postResultSetMapper.map(resultSet);

        // Assert
        assertEquals(1L, post.getId());
        assertEquals("Content 1", post.getContent());
        assertEquals("Title 1", post.getTitle());
        assertEquals(1L, post.getUserId());

        List<TagEntity> tags = post.getTags();
        assertNotNull(tags);
        assertEquals(1, tags.size());
        assertEquals(1L, tags.get(0).getId());
        assertEquals("Tag 1", tags.get(0).getName());
//        assertEquals(2L, tags.get(1).getId());
//        assertEquals("Tag 2", tags.get(1).getName());
    }

    @Test
    void testMapWithNoTags() throws SQLException {
        // Arrange
        Mockito.when(resultSet.getLong("post_id")).thenReturn(1L);
        Mockito.when(resultSet.getString("content")).thenReturn("Content 1");
        Mockito.when(resultSet.getString("title")).thenReturn("Title 1");
        Mockito.when(resultSet.getLong("user_id")).thenReturn(1L);

        // Убедитесь, что ResultSet не возвращает данные о тегах

        // Act
        PostEntity post = postResultSetMapper.map(resultSet);

        // Assert
        assertEquals(1L, post.getId());
        assertEquals("Content 1", post.getContent());
        assertEquals("Title 1", post.getTitle());
        assertEquals(1L, post.getUserId());

        List<TagEntity> tags = post.getTags();
        assertNull(tags);
    }


    @Test
    void testToListPosts() throws SQLException {
        // Arrange
        Mockito.when(resultSet.next()).thenReturn(true, true, false);
        Mockito.when(resultSet.getLong("post_id")).thenReturn(1L, 2L);
        Mockito.when(resultSet.getString("content")).thenReturn("Content 1", "Content 2");
        Mockito.when(resultSet.getString("title")).thenReturn("Title 1", "Title 2");
        Mockito.when(resultSet.getLong("user_id")).thenReturn(1L, 2L);

        // Act
        List<PostEntity> posts = postResultSetMapper.toListPosts(resultSet);

        // Assert
//        assertEquals(2, posts.size());

        assertEquals(1L, posts.get(0).getId());
        assertEquals("Content 1", posts.get(0).getContent());
        assertEquals("Title 1", posts.get(0).getTitle());
        assertEquals(1L, posts.get(0).getUserId());

        assertEquals(2L, posts.get(1).getId());
        assertEquals("Content 2", posts.get(1).getContent());
        assertEquals("Title 2", posts.get(1).getTitle());
        assertEquals(2L, posts.get(1).getUserId());
    }
}
