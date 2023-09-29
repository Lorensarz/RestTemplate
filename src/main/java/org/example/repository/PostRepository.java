package org.example.repository;

import org.example.model.PostEntity;
import org.example.model.TagEntity;

import java.util.List;

public interface PostRepository {
    PostEntity findById(long id);
    List<PostEntity> findAll();
    void save(PostEntity post);
    void update(PostEntity post);
    void delete(long id);
    List<TagEntity> findTagsByPost(long postId);
    void addTagToPost(PostEntity post, TagEntity tag);
    void removeTagFromPost(PostEntity post, TagEntity tag);
}
