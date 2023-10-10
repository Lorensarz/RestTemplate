package org.example.service.impl;

import org.example.model.PostEntity;
import org.example.model.TagEntity;
import org.example.repository.PostRepository;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class PostServiceImplTest {

    @Mock
    private PostRepository repository;
    @Mock
    private PostDtoMapper postDtoMapper;
    @Mock
    private TagDtoMapper tagDtoMapper;

    private PostServiceImpl postService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        postService = new PostServiceImpl(repository, postDtoMapper, tagDtoMapper);
    }

    @Test
    public void testFindById() {
        long postId = 1L;
        PostEntity expectedPost = new PostEntity();
        expectedPost.setId(postId);
        when(repository.findById(postId)).thenReturn(expectedPost);

        PostEntity result = postService.findById(postId);
        verify(repository).findById(postId);

        assertNotNull(result);
        assertEquals(postId, result.getId());
    }

    @Test
    public void testFindAll() {
        List<PostEntity> postEntities = new ArrayList<>();

        when(repository.findAll()).thenReturn(postEntities);

        List<PostEntity> result = postService.findAll();

        verify(repository).findAll();

        // Добавьте здесь проверку результата, если это необходимо
    }

    @Test
    public void testSave() {
        PostDto postDto = new PostDto();
        PostEntity postEntity = new PostEntity();

        when(postDtoMapper.toEntity(postDto)).thenReturn(postEntity);

        postService.save(postDto);

        verify(postDtoMapper).toEntity(postDto);
        verify(repository).save(postEntity);
    }

    @Test
    public void testUpdate() {
        PostDto postDto = new PostDto();
        PostEntity postEntity = new PostEntity();

        when(postDtoMapper.toEntity(postDto)).thenReturn(postEntity);

        postService.update(postDto);

        verify(postDtoMapper).toEntity(postDto);
        verify(repository).update(postEntity);
    }

    @Test
    public void testDeleteById() {
        long postId = 1L;

        postService.deleteById(postId);

        verify(repository).delete(postId);
    }


    @Test
    public void testFindPostsByTag() {
        TagDto tagDto = new TagDto();
        TagEntity tagEntity = new TagEntity();
        List<PostEntity> postEntities = new ArrayList<>();
        List<PostDto> postDtos = new ArrayList<>();

        when(tagDtoMapper.toEntity(tagDto)).thenReturn(tagEntity);
        when(repository.findPostsByTag(tagEntity)).thenReturn(postEntities);
        when(postDtoMapper.toDtoList(postEntities)).thenReturn(postDtos);

        List<PostDto> result = postService.findPostsByTag(tagDto);

        verify(tagDtoMapper).toEntity(tagDto);
        verify(repository).findPostsByTag(tagEntity);
        verify(postDtoMapper).toDtoList(postEntities);
    }
}