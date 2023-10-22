package org.example.repository.mapper;

import org.example.model.TagEntity;
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
        when(resultSet.next()).thenReturn(true, true, false);
        when(resultSet.getLong("tag_id")).thenReturn(1L, 2L);
        when(resultSet.getString("tag_name")).thenReturn("Tag 1", "Tag 2");

        List<TagEntity> tags = tagResultSetMapper.map(resultSet);

        assertEquals(2, tags.size());
        assertEquals(1L, tags.get(0).getId());
        assertEquals(2L, tags.get(1).getId());
        assertEquals("Tag 1", tags.get(0).getName());
        assertEquals("Tag 2", tags.get(1).getName());
    }
}
