package org.example.repository.mapper;

import org.example.model.PostEntity;
import org.example.model.TagEntity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PostResultSetMapper implements SimpleResultSetMapper {
    @Override
    public PostEntity mapPost(ResultSet resultSet) throws SQLException {
        PostEntity post = new PostEntity();
        post.setId(resultSet.getLong("id"));
        post.setContent(resultSet.getString("content"));
        post.setTitle(resultSet.getString("title"));
        return post;
    }

    @Override
    public TagEntity mapTag(ResultSet resultSet) throws SQLException {
        TagEntity tag = new TagEntity();
        tag.setId(resultSet.getLong("id"));
        tag.setName(resultSet.getString("name"));
        return tag;
    }

    @Override
    public List<TagEntity> toDtoListTags(ResultSet resultSet) throws SQLException {
        List<TagEntity> tags = new ArrayList<>();
        while (resultSet.next()) {
            tags.add(mapTag(resultSet));
        }
        return tags;
    }

    @Override
    public List<PostEntity> toDtoListPost(ResultSet resultSet) throws SQLException {
        List<PostEntity> posts = new ArrayList<>();
        while (resultSet.next()) {
            posts.add(mapPost(resultSet));
        }
        return posts;
    }
}
