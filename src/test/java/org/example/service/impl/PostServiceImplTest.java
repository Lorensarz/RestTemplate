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
        MockitoAnnotations.openMocks(this);
        postService = new PostServiceImpl(repository, postDtoMapper, tagDtoMapper);
    }

    @Test
    public void testFindPostsByUsersId() {
        long userId = 1L;

        PostEntity postEntity = new PostEntity();
        postEntity.setId(1L);
        postEntity.setTitle("Post 1");
        postEntity.setContent("Content for post 1");
        postEntity.setUserId(1L);

        PostEntity postEntity2 = new PostEntity();
        postEntity2.setId(2L);
        postEntity2.setTitle("Post 2");
        postEntity2.setContent("Content for post 2");
        postEntity2.setUserId(2L);

        List<PostEntity> expectedPosts = new ArrayList<>();
        expectedPosts.add(postEntity);
        expectedPosts.add(postEntity2);

        when(repository.findPostsByUserId(userId)).thenReturn(expectedPosts);

        List<PostEntity> result = postService.findPostsByUserId(userId);
        verify(repository).findPostsByUserId(userId);

        assertNotNull(result);
        assertEquals(expectedPosts, result);
    }

    @Test
    public void testFindAll() {
        List<PostEntity> postEntities = new ArrayList<>();

        when(repository.findAll()).thenReturn(postEntities);

        postService.findAll();

        verify(repository).findAll();

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

        postService.findPostsByTag(tagDto);

        verify(tagDtoMapper).toEntity(tagDto);
        verify(repository).findPostsByTag(tagEntity);
        verify(postDtoMapper).toDtoList(postEntities);
    }
}