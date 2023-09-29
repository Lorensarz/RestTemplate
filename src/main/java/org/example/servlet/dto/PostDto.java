package org.example.servlet.dto;

import java.util.List;

public class PostDto {

    private long id;
    private String title;
    private String content;
    private long userId;
    private List<TagDto> tags;

    public PostDto() {
    }

    public PostDto(long id, String title, String content, long userId, List<TagDto> tags) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.userId = userId;
        this.tags = tags;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public List<TagDto> getTags() {
        return tags;
    }

    public void setTags(List<TagDto> tags) {
        this.tags = tags;
    }
}
