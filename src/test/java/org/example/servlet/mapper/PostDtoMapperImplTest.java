package org.example.servlet.mapper;

import org.example.model.PostEntity;
import org.example.model.TagEntity;
import org.example.servlet.dto.PostDto;
import org.example.servlet.dto.TagDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class PostDtoMapperImplTest {

    private PostDtoMapperImpl postDtoMapper;
    private TagDtoMapper tagDtoMapper;

    @BeforeEach
    public void setUp() {
        postDtoMapper = new PostDtoMapperImpl();
        tagDtoMapper = new TagDtoMapperImpl();
    }

    @Test
    public void testToDto() {
        PostEntity postEntity = new PostEntity();
        List<TagEntity> tagsEntity = new ArrayList<>();
        TagDto tagDto = new TagDto(1L, "New Tag");
        tagsEntity.add(tagDtoMapper.toEntity(tagDto));
        postEntity.setId(1L);
        postEntity.setTitle("Test Post");
        postEntity.setContent("This is a test post.");
        postEntity.setUserId(101L);
        postEntity.setTags(tagsEntity);

        PostDto postDto = postDtoMapper.toDto(postEntity);

        assertNotNull(postDto);
        assertEquals(postEntity.getId(), postDto.getId());
        assertEquals(postEntity.getTitle(), postDto.getTitle());
        assertEquals(postEntity.getContent(), postDto.getContent());
        assertEquals(postEntity.getUserId(), postDto.getUserId());

        // Сравниваем списки тегов
        List<TagDto> tagsDto = postDto.getTags();

        assertEquals(tagsEntity.size(), tagsDto.size());

        for (int i = 0; i < tagsEntity.size(); i++) {
            TagEntity tagEntity = tagsEntity.get(i);
            TagDto tag = tagsDto.get(i);

            assertEquals(tagEntity.getId(), tag.getId());
            assertEquals(tagEntity.getName(), tag.getName());
        }
    }

    @Test
    public void testToEntity() {
        PostDto postDto = new PostDto();
        postDto.setId(1L);
        postDto.setTitle("Test Post");
        postDto.setContent("This is a test post.");
        postDto.setUserId(101L);

        PostEntity postEntity = postDtoMapper.toEntity(postDto);

        assertNotNull(postEntity);
        assertEquals(postDto.getId(), postEntity.getId());
        assertEquals(postDto.getTitle(), postEntity.getTitle());
        assertEquals(postDto.getContent(), postEntity.getContent());
        assertEquals(postDto.getUserId(), postEntity.getUserId());
    }

    @Test
    public void testToDtoList() {
        List<PostEntity> postEntities = new ArrayList<>();
        List<TagEntity> tagsEntity = new ArrayList<>();
        TagDto tagDto = new TagDto(1L, "New Tag");
        tagsEntity.add(tagDtoMapper.toEntity(tagDto));
        for (int i = 1; i <= 3; i++) {
            PostEntity postEntity = new PostEntity();
            postEntity.setId((long) i);
            postEntity.setTitle("Test Post " + i);
            postEntity.setContent("This is a test post " + i + ".");
            postEntity.setUserId((long) (100 + i));
            postEntity.setTags(tagsEntity);
            postEntities.add(postEntity);
        }

        List<PostDto> postDtos = postDtoMapper.toDtoList(postEntities);

        assertNotNull(postDtos);
        assertEquals(postEntities.size(), postDtos.size());

        for (int i = 0; i < postEntities.size(); i++) {
            assertEquals(postEntities.get(i).getId(), postDtos.get(i).getId());
            assertEquals(postEntities.get(i).getTitle(), postDtos.get(i).getTitle());
            assertEquals(postEntities.get(i).getContent(), postDtos.get(i).getContent());
            assertEquals(postEntities.get(i).getUserId(), postDtos.get(i).getUserId());
            assertEquals(postEntities.get(i).getTags().size(), postDtos.get(i).getTags().size());

            for (int j = 0; j < postEntities.get(i).getTags().size(); j++) {
                assertEquals(postEntities.get(i).getTags().get(j).getId(),
                        postDtos.get(i).getTags().get(j).getId());
                assertEquals(postEntities.get(i).getTags().get(j).getName(),
                        postDtos.get(i).getTags().get(j).getName());
            }
        }
    }

    @Test
    public void testToEntityList() {
        List<PostDto> postDtos = new ArrayList<>();
        for (int i = 1; i <= 3; i++) {
            PostDto postDto = new PostDto();
            postDto.setId((long) i);
            postDto.setTitle("Test Post " + i);
            postDto.setContent("This is a test post " + i + ".");
            postDto.setUserId((long) (100 + i));
            postDtos.add(postDto);
        }

        List<PostEntity> postEntities = postDtoMapper.toEntityList(postDtos);

        assertNotNull(postEntities);
        assertEquals(postDtos.size(), postEntities.size());

        for (int i = 0; i < postDtos.size(); i++) {
            assertEquals(postDtos.get(i).getId(), postEntities.get(i).getId());
            assertEquals(postDtos.get(i).getTitle(), postEntities.get(i).getTitle());
            assertEquals(postDtos.get(i).getContent(), postEntities.get(i).getContent());
            assertEquals(postDtos.get(i).getUserId(), postEntities.get(i).getUserId());
        }
    }
}
