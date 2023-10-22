package org.example.repository;

import org.example.model.PostEntity;
import org.example.model.TagEntity;

import java.sql.Connection;
import java.util.List;

public interface TagRepository {
    List<TagEntity> findTagsByPostId(PostEntity postEntity);

    void addTagToPost(PostEntity post);
    void removeTagFromPost(PostEntity post);

    void updateTagsForPost(PostEntity post);
}
