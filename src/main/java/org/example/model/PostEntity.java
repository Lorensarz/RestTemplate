package org.example.model;

import java.util.List;
import java.util.Objects;

public class PostEntity {

    private long id;
    private String title;
    private String content;
    private long userId;
    private List<TagEntity> tags;

    public PostEntity() {
    }

    public PostEntity(long id, String title, String content, long userId, List<TagEntity> tags) {
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

    public List<TagEntity> getTags() {
        return tags;
    }

    public void setTags(List<TagEntity> tags) {
        this.tags = tags;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PostEntity that = (PostEntity) o;
        return id == that.id && userId == that.userId && Objects.equals(title, that.title) &&
                Objects.equals(content, that.content) &&
                Objects.equals(tags, that.tags);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, content, userId, tags);
    }

    @Override
    public String toString() {
        return "PostEntity{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", userId=" + userId +
                ", tags=" + tags +
                '}';
    }
}
