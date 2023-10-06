package org.example.service.impl;

import org.example.model.PostEntity;
import org.example.model.TagEntity;
import org.example.repository.PostRepository;
import org.example.repository.impl.PostRepositoryImpl;
import org.example.service.PostService;
import org.example.servlet.dto.PostDto;
import org.example.servlet.dto.TagDto;
import org.example.servlet.mapper.PostDtoMapper;
import org.example.servlet.mapper.PostDtoMapperImpl;
import org.example.servlet.mapper.TagDtoMapper;
import org.example.servlet.mapper.TagDtoMapperImpl;

import java.util.List;

public class PostServiceImpl implements PostService {
    private final PostRepository repository = new PostRepositoryImpl();
    private final PostDtoMapper postDtoMapper = new PostDtoMapperImpl();
    private final TagDtoMapper tagDtoMapper = new TagDtoMapperImpl();

    private PostEntity getPostEntity(PostDto postDto) {
        return postDtoMapper.toEntity(postDto);
    }

    @Override
    public PostEntity findById(PostDto postDto) {
        return repository.findById(getPostEntity(postDto));
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
    public void deleteById(PostDto postDto) {
        repository.delete(getPostEntity(postDto));
    }

    @Override
    public List<TagDto> findTagsByPostId(PostDto postDto) {
        List<TagEntity> tags = repository.findTagsByPostId(getPostEntity(postDto));
        return tagDtoMapper.toDtoList(tags);
    }
}
