package org.example.repository.mapper;

import org.example.model.PostEntity;
import org.example.model.TagEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

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
        Mockito.when(resultSet.getLong("post_id")).thenReturn(1L);
        Mockito.when(resultSet.getString("content")).thenReturn("Content 1");
        Mockito.when(resultSet.getString("title")).thenReturn("Title 1");
        Mockito.when(resultSet.getLong("user_id")).thenReturn(1L);


        PostEntity post = postResultSetMapper.map(resultSet);

        assertEquals(1L, post.getId());
        assertEquals("Content 1", post.getContent());
        assertEquals("Title 1", post.getTitle());
        assertEquals(1L, post.getUserId());
    }

    @Test
    void testMapWithNoTags() throws SQLException {
        Mockito.when(resultSet.getLong("post_id")).thenReturn(1L);
        Mockito.when(resultSet.getString("content")).thenReturn("Content 1");
        Mockito.when(resultSet.getString("title")).thenReturn("Title 1");
        Mockito.when(resultSet.getLong("user_id")).thenReturn(1L);

        PostEntity post = postResultSetMapper.map(resultSet);

        assertEquals(1L, post.getId());
        assertEquals("Content 1", post.getContent());
        assertEquals("Title 1", post.getTitle());
        assertEquals(1L, post.getUserId());

        List<TagEntity> tags = post.getTags();
        assertNull(tags);
    }


    @Test
    void testToListPosts() throws SQLException {
        Mockito.when(resultSet.next()).thenReturn(true, true, false);
        Mockito.when(resultSet.getLong("post_id")).thenReturn(1L, 2L);
        Mockito.when(resultSet.getString("content")).thenReturn("Content 1", "Content 2");
        Mockito.when(resultSet.getString("title")).thenReturn("Title 1", "Title 2");
        Mockito.when(resultSet.getLong("user_id")).thenReturn(1L, 2L);

        List<PostEntity> posts = postResultSetMapper.toListPosts(resultSet);

        assertEquals(2, posts.size());

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
