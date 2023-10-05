package org.example.service;

import org.example.model.PostEntity;
import org.example.model.TagEntity;

import java.util.List;

public interface PostService {
    PostEntity findById(long id);
    List<PostEntity> findAll();
    void save(PostEntity post);
    void update(PostEntity post);
    void deleteById(long id);
    List<TagEntity> findTagsByPostId(long postId);
}
