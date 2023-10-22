package org.example.repository.mapper;

import org.example.model.PostEntity;
import org.example.model.TagEntity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PostResultSetMapperImpl implements PostResultSetMapper {
    private final TagResultSetMapper tagResultSetMapper = new TagResultSetMapperImpl();


    @Override
    public PostEntity map(ResultSet resultSet) throws SQLException {
        PostEntity post = new PostEntity();
        post.setId(resultSet.getLong("post_id"));
        post.setContent(resultSet.getString("content"));
        post.setTitle(resultSet.getString("title"));
        post.setUserId(resultSet.getLong("user_id"));

        return post;
    }

    @Override
    public List<PostEntity> toListPosts(ResultSet resultSet) throws SQLException {
        List<PostEntity> posts = new ArrayList<>();

        while (resultSet.next()) {
            PostEntity postEntity = map(resultSet);
            posts.add(postEntity);
        }
        return posts;
    }
}
