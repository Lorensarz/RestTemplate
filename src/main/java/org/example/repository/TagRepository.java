package org.example.repository;

import org.example.model.PostEntity;
import org.example.model.TagEntity;

import java.util.List;

public interface TagRepository {
    List<TagEntity> findTagsByPostId(PostEntity postEntity);

    void addTagToPost(PostEntity post, TagEntity tag);
    void removeTagFromPost(PostEntity post, TagEntity tag);
}
