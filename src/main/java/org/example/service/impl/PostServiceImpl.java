package org.example.service.impl;

import org.example.model.PostEntity;
import org.example.model.TagEntity;
import org.example.repository.PostRepository;
import org.example.service.PostService;
import org.example.servlet.dto.PostDto;
import org.example.servlet.dto.TagDto;
import org.example.servlet.mapper.PostDtoMapper;
import org.example.servlet.mapper.TagDtoMapper;

import java.sql.SQLException;
import java.util.List;

public class PostServiceImpl implements PostService {
    private final PostRepository repository;
    private final PostDtoMapper postDtoMapper;
    private final TagDtoMapper tagDtoMapper;

    public PostServiceImpl(PostRepository repository, PostDtoMapper postDtoMapper, TagDtoMapper tagDtoMapper) {
        this.repository = repository;
        this.postDtoMapper = postDtoMapper;
        this.tagDtoMapper = tagDtoMapper;
    }

    private PostEntity getPostEntity(PostDto postDto) {
        return postDtoMapper.toEntity(postDto);
    }

    @Override
    public List<PostEntity> findPostsByUserId(long id) {
        return repository.findPostsByUserId(id);
    }

    @Override
    public List<PostEntity> findAll() {
        return repository.findAll();
    }

    @Override
    public void save(PostDto postDto) {
        repository.save(getPostEntity(postDto));
    }

    @Override
    public void update(PostDto postDto) {
        repository.update(getPostEntity(postDto));
    }

    @Override
    public void deleteById(long id) {
        repository.delete(id);
    }

    @Override
    public List<PostDto> findPostsByTag(TagDto tagDto) {
        TagEntity tagEntity = tagDtoMapper.toEntity(tagDto);
        List<PostEntity> postsDto = repository.findPostsByTag(tagEntity);
        return postDtoMapper.toDtoList(postsDto);
    }
}
