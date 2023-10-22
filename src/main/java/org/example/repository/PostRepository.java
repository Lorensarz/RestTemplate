package org.example.repository;

import org.example.model.PostEntity;
import org.example.model.TagEntity;

import java.sql.SQLException;
import java.util.List;

public interface PostRepository {
//    PostEntity findById(long id);

    List<PostEntity> findPostsByUserId(long userId);

    List<PostEntity> findAll();
    void save(PostEntity post);
    void update(PostEntity post);
    List<PostEntity> findPostsByTag(TagEntity tagEntity);

    PostEntity findPostByPostId(Long postId);

    void delete(long id);

}
