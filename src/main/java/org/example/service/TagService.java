package org.example.service;

import org.example.servlet.dto.PostDto;
import org.example.servlet.dto.TagDto;

import java.util.List;

public interface TagService {
    void addTagToPost(PostDto post);
    void removeTagFromPost(PostDto post, TagDto tag);
    List<TagDto> findTagsByPost(PostDto postDto);
    void updateTagForPost(PostDto postDto);
}
