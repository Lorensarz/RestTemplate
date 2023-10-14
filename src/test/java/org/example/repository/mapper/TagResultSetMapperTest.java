package org.example.repository.mapper;

import org.example.model.TagEntity;
import org.example.repository.mapper.TagResultSetMapper;
import org.example.repository.mapper.TagResultSetMapperImpl;
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
class TagResultSetMapperTest {

    private TagResultSetMapper tagResultSetMapper;

    @Mock
    private ResultSet resultSet;

    @BeforeEach
    void setUp() {
        tagResultSetMapper = new TagResultSetMapperImpl();
    }

    @Test
    void testMap() throws SQLException {
        when(resultSet.getString("name")).thenReturn("Tag Name");

        TagEntity tag = tagResultSetMapper.map(resultSet);

        assertEquals("Tag Name", tag.getName());
    }

    @Test
    void testToListTags() throws SQLException {
        when(resultSet.next()).thenReturn(true, true, false);
        when(resultSet.getString("name")).thenReturn("Tag 1", "Tag 2");

        List<TagEntity> tags = tagResultSetMapper.toListTags(resultSet);

        assertEquals(2, tags.size());
        assertEquals("Tag 1", tags.get(0).getName());
        assertEquals("Tag 2", tags.get(1).getName());
    }
}