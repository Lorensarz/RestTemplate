package org.example.repository;

import org.example.model.PostEntity;
import org.example.model.TagEntity;

import java.util.List;

public interface TagRepository {
    void addTagToPost(PostEntity post, TagEntity tag);
    void removeTagFromPost(PostEntity post, TagEntity tag);
    List<PostEntity> findPostsByTag(TagEntity tagEntity);
}
