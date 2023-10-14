package org.example.repository.mapper;

import org.example.model.PostEntity;
import org.example.repository.mapper.PostResultSetMapper;
import org.example.repository.mapper.PostResultSetMapperImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PostResultSetMapperTest {

    private PostResultSetMapper postResultSetMapper;

    @Mock
    private ResultSet resultSet;

    @BeforeEach
    void setUp() {
        postResultSetMapper = new PostResultSetMapperImpl();
    }

    @Test
    void testMap() throws SQLException {
        when(resultSet.getLong("id")).thenReturn(1L);
        when(resultSet.getString("content")).thenReturn("Sample content");
        when(resultSet.getString("title")).thenReturn("Sample title");

        PostEntity post = postResultSetMapper.map(resultSet);

        assertEquals(1L, post.getId());
        assertEquals("Sample content", post.getContent());
        assertEquals("Sample title", post.getTitle());
    }

    @Test
    void testToListPosts() throws SQLException {
        when(resultSet.next()).thenReturn(true, true, false);
        when(resultSet.getLong("id")).thenReturn(1L, 2L);
        when(resultSet.getString("content")).thenReturn("Content 1", "Content 2");
        when(resultSet.getString("title")).thenReturn("Title 1", "Title 2");

        List<PostEntity> posts = postResultSetMapper.toListPosts(resultSet);

        assertEquals(2, posts.size());

        assertEquals(1L, posts.get(0).getId());
        assertEquals("Content 1", posts.get(0).getContent());
        assertEquals("Title 1", posts.get(0).getTitle());

        assertEquals(2L, posts.get(1).getId());
        assertEquals("Content 2", posts.get(1).getContent());
        assertEquals("Title 2", posts.get(1).getTitle());
    }
}
