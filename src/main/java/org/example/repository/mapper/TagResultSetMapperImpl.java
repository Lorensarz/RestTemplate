package org.example.repository.mapper;

import org.example.model.PostEntity;
import org.example.model.TagEntity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TagResultSetMapperImpl implements TagResultSetMapper {
    PostResultSetMapper postResultSetMapper = new PostResultSetMapperImpl();

    @Override
    public TagEntity map(ResultSet resultSet) throws SQLException {
        TagEntity tag = new TagEntity();
        tag.setName(resultSet.getString("name"));
        return tag;
    }

    @Override
    public List<PostEntity> toListPosts(ResultSet resultSet) {
        List<PostEntity> posts = new ArrayList<>();
        try {
            while (resultSet.next()) {
                posts.add(postResultSetMapper.map(resultSet));
            }
            return posts;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
