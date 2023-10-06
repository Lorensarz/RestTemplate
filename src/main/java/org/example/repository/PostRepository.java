package org.example.repository;

import org.example.model.PostEntity;
import org.example.model.TagEntity;

import java.util.List;

public interface PostRepository {
    PostEntity findById(PostEntity postEntity);
    List<PostEntity> findAll();
    void save(PostEntity post);
    void update(PostEntity post);

    List<TagEntity> findTagsByPostId(PostEntity postEntity);

    void delete(PostEntity postEntity);

}
