package org.example.repository.mapper;

import org.example.model.PostEntity;
import org.example.model.TagEntity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public interface PostResultSetMapper {
    PostEntity map(ResultSet resultSet) throws SQLException;
    List<PostEntity> toListPosts(ResultSet resultSet);

}
