package org.example.repository.mapper;

import org.example.model.PostEntity;
import org.example.model.TagEntity;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PostResultSetMapperImpl implements PostResultSetMapper {

    @Override
    public PostEntity map(ResultSet resultSet) throws SQLException {
        PostEntity post = new PostEntity();
        post.setId(resultSet.getLong("id"));
        post.setContent(resultSet.getString("content"));
        post.setTitle(resultSet.getString("title"));
        return post;
    }

    @Override
    public List<PostEntity> toListPosts(ResultSet resultSet) {
        List<PostEntity> posts = new ArrayList<>();
        try {
            while (resultSet.next()) {
                posts.add(map(resultSet));
            }
            return posts;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
