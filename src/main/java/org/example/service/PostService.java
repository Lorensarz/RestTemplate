package org.example.service;

import org.example.model.PostEntity;

import java.util.List;

public interface PostService {
    PostEntity findById(long id);
    List<PostEntity> findAll();
    void save(PostEntity post);
    void update(PostEntity post);
    void delete(long id);
}
