package org.example.repository.mapper;

import org.example.model.TagEntity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TagResultSetMapperImpl implements TagResultSetMapper {

    @Override
    public List<TagEntity> map(ResultSet resultSet) throws SQLException {
        List<TagEntity> tags = new ArrayList<>();
        while (resultSet.next()) {
            TagEntity tag = new TagEntity();
            tag.setId(resultSet.getLong("tag_id"));
            tag.setName(resultSet.getString("tag_name"));
            tags.add(tag);
        }
        return tags;
    }
}
