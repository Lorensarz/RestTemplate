package org.example.service;

import org.example.model.PostEntity;
import org.example.servlet.dto.PostDto;
import org.example.servlet.dto.TagDto;

import java.util.List;

public interface PostService {
    PostEntity findById(PostDto postDto);
    List<PostEntity> findAll();
    void save(PostDto postDto);
    void update(PostDto postDto);
    void deleteById(PostDto postDto);
    List<TagDto> findTagsByPostId(PostDto postDto);
}
