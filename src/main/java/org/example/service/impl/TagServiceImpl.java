package org.example.service.impl;

import org.example.model.PostEntity;
import org.example.model.TagEntity;
import org.example.repository.TagRepository;
import org.example.repository.impl.TagRepositoryImpl;
import org.example.service.TagService;
import org.example.servlet.dto.PostDto;
import org.example.servlet.dto.TagDto;
import org.example.servlet.mapper.PostDtoMapper;
import org.example.servlet.mapper.PostDtoMapperImpl;
import org.example.servlet.mapper.TagDtoMapper;
import org.example.servlet.mapper.TagDtoMapperImpl;

import java.util.List;

public class TagServiceImpl implements TagService {

    private final TagRepository repository;
    private final TagDtoMapper tagDtoMapper;
    private final PostDtoMapper postDtoMapper;

    public TagServiceImpl(TagRepository repository, TagDtoMapper tagDtoMapper, PostDtoMapper postDtoMapper) {
        this.repository = repository;
        this.tagDtoMapper = tagDtoMapper;
        this.postDtoMapper = postDtoMapper;
    }

    @Override
    public void addTagToPost(PostDto post) {
        PostEntity postEntity = postDtoMapper.toEntity(post);
        repository.addTagToPost(postEntity);
    }

    @Override
    public void updateTagForPost(PostDto post) {
        PostEntity postEntity = postDtoMapper.toEntity(post);
        repository.updateTagsForPost(postEntity);
    }

    @Override
    public void removeTagFromPost(PostDto post) {
        PostEntity postEntity = postDtoMapper.toEntity(post);
        repository.removeTagFromPost(postEntity);
    }

    @Override
    public List<TagDto> findTagsByPost(PostDto postDto) {
        List<TagEntity> tags = repository.findTagsByPostId(postDtoMapper.toEntity(postDto));
        return tagDtoMapper.toDtoList(tags);
    }


}
