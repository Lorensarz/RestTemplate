package org.example.repository.mapper;

import org.example.model.TagEntity;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TagResultSetMapper implements SimpleResultSetMapper {
    @Override
    public TagEntity map(ResultSet resultSet) throws SQLException {
        TagEntity tag = new TagEntity();
        tag.setName(resultSet.getString("name"));
        return tag;
    }
}
