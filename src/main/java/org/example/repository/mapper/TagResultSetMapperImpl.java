package org.example.repository.mapper;

import org.example.model.PostEntity;
import org.example.model.TagEntity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TagResultSetMapperImpl implements TagResultSetMapper {

    @Override
    public TagEntity map(ResultSet resultSet) throws SQLException {
        TagEntity tag = new TagEntity();
        tag.setName(resultSet.getString("name"));
        return tag;
    }

    public List<TagEntity> toListTags(ResultSet resultSet) {
        List<TagEntity> tags = new ArrayList<>();
        try {
            while (resultSet.next()) {
                tags.add(map(resultSet));
            }
            return tags;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
