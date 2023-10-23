package org.example.service.impl;

import org.example.model.PostEntity;
import org.example.model.TagEntity;
import org.example.repository.TagRepository;
import org.example.servlet.dto.PostDto;
import org.example.servlet.dto.TagDto;
import org.example.servlet.mapper.PostDtoMapper;
import org.example.servlet.mapper.TagDtoMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class TagServiceImplTest {

    @Mock
    private TagRepository repository;
    @Mock
    private TagDtoMapper tagDtoMapper;
    @Mock
    private PostDtoMapper postDtoMapper;

    private TagServiceImpl tagService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        tagService = new TagServiceImpl(repository, tagDtoMapper, postDtoMapper);
    }

    @Test
    public void testAddTagToPost() {
        PostDto postDto = new PostDto();
        PostEntity postEntity = new PostEntity();

        when(postDtoMapper.toEntity(postDto)).thenReturn(postEntity);

        tagService.addTagToPost(postDto);

        verify(postDtoMapper).toEntity(postDto);
        verify(repository).addTagToPost(postEntity);
    }

    @Test
    void testUpdateTagForPost() {
        PostDto postDto = new PostDto();
        PostEntity postEntity = new PostEntity();

        when(postDtoMapper.toEntity(postDto)).thenReturn(postEntity);

        tagService.updateTagForPost(postDto);

        verify(postDtoMapper).toEntity(postDto);
        verify(repository).updateTagsForPost(postEntity);
    }

    @Test
    public void testRemoveTagFromPost() {
        PostDto postDto = new PostDto();
        PostEntity postEntity = new PostEntity();

        when(postDtoMapper.toEntity(postDto)).thenReturn(postEntity);

        tagService.removeTagFromPost(postDto);

        verify(postDtoMapper).toEntity(postDto);
        verify(repository).removeTagFromPost(postEntity);
    }

    @Test
    public void testFindTagsByPost() {
        PostDto postDto = new PostDto();
        PostEntity postEntity = new PostEntity();
        List<TagEntity> tagEntities = new ArrayList<>();
        List<TagDto> tagDtos = new ArrayList<>();

        when(postDtoMapper.toEntity(postDto)).thenReturn(postEntity);
        when(repository.findTagsByPostId(postEntity)).thenReturn(tagEntities);
        when(tagDtoMapper.toDtoList(tagEntities)).thenReturn(tagDtos);

        tagService.findTagsByPost(postDto);

        verify(postDtoMapper).toEntity(postDto);
        verify(repository).findTagsByPostId(postEntity);
        verify(tagDtoMapper).toDtoList(tagEntities);
    }
}
