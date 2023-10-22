package org.example.servlet.mapper;

import org.example.model.TagEntity;
import org.example.servlet.dto.TagDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class TagDtoMapperImplTest {

    private TagDtoMapperImpl tagDtoMapper;

    @BeforeEach
    public void setUp() {
        tagDtoMapper = new TagDtoMapperImpl();
    }

    @Test
    public void testToDto() {
        TagEntity tagEntity = new TagEntity();
        tagEntity.setId(1L);
        tagEntity.setName("Test Tag");

        TagDto tagDto = tagDtoMapper.toDto(tagEntity);

        assertNotNull(tagDto);
        assertEquals(tagEntity.getId(), tagDto.getId());
        assertEquals(tagEntity.getName(), tagDto.getName());
    }

    @Test
    public void testToEntity() {
        TagDto tagDto = new TagDto();
        tagDto.setId(1L);
        tagDto.setName("Test Tag");

        TagEntity tagEntity = tagDtoMapper.toEntity(tagDto);

        assertNotNull(tagEntity);
        assertEquals(tagDto.getId(), tagEntity.getId());
        assertEquals(tagDto.getName(), tagEntity.getName());
    }

    @Test
    public void testToDtoList() {
        List<TagEntity> tagEntities = new ArrayList<>();
        for (int i = 1; i <= 3; i++) {
            TagEntity tagEntity = new TagEntity();
            tagEntity.setId(i);
            tagEntity.setName("Test Tag " + i);
            tagEntities.add(tagEntity);
        }

        List<TagDto> tagDtos = tagDtoMapper.toDtoList(tagEntities);

        assertNotNull(tagDtos);
        assertEquals(tagEntities.size(), tagDtos.size());

        for (int i = 0; i < tagEntities.size(); i++) {
            assertEquals(tagEntities.get(i).getId(), tagDtos.get(i).getId());
            assertEquals(tagEntities.get(i).getName(), tagDtos.get(i).getName());
        }
    }

    @Test
    public void testToEntityList() {
        List<TagDto> tagDtos = new ArrayList<>();
        for (int i = 1; i <= 3; i++) {
            TagDto tagDto = new TagDto();
            tagDto.setId(i);
            tagDto.setName("Test Tag " + i);
            tagDtos.add(tagDto);
        }

        List<TagEntity> tagEntities = tagDtoMapper.toEntityList(tagDtos);

        assertNotNull(tagEntities);
        assertEquals(tagDtos.size(), tagEntities.size());

        for (int i = 0; i < tagDtos.size(); i++) {
            assertEquals(tagDtos.get(i).getId(), tagEntities.get(i).getId());
            assertEquals(tagDtos.get(i).getName(), tagEntities.get(i).getName());
        }
    }
}
