package org.example.repository.mapper;

import org.example.model.PostEntity;
import org.example.model.TagEntity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public interface TagResultSetMapper {
    TagEntity map(ResultSet resultSet) throws SQLException;
    List<TagEntity> toListTags(ResultSet resultSet);
}
