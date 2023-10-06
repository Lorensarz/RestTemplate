package org.example.service;

import org.example.servlet.dto.PostDto;
import org.example.servlet.dto.TagDto;

import java.util.List;

public interface TagService {
    void addTagToPost(PostDto post, TagDto tag);
    void removeTagFromPost(PostDto post, TagDto tag);
    List<PostDto> findPostsByTag(TagDto tagDto);
}
