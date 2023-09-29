package org.example.repository.mapper;

import org.example.model.PostEntity;
import org.example.model.TagEntity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public interface SimpleResultSetMapper {

    PostEntity mapPost(ResultSet resultSet) throws SQLException;

    TagEntity mapTag(ResultSet resultSet) throws SQLException;

    List<TagEntity> toDtoListTags(ResultSet resultSet) throws SQLException;

    List<PostEntity> toDtoListPost(ResultSet resultSet) throws SQLException;
}
