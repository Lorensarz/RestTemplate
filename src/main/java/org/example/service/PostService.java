package org.example.service;

import org.example.model.PostEntity;
import org.example.servlet.dto.PostDto;
import org.example.servlet.dto.TagDto;

import java.sql.SQLException;
import java.util.List;

public interface PostService {
//    PostEntity findById(long id);

    List<PostEntity> findPostsByUserId(long id);

    List<PostEntity> findAll();
    void save(PostDto postDto);
    void update(PostDto postDto);
    void deleteById(long id);
    List<PostDto> findPostsByTag(TagDto tagDto);
}
