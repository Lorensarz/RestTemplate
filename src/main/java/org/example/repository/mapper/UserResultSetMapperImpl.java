package org.example.repository.mapper;

import org.example.model.UserEntity;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserResultSetMapperImpl implements UserResultSetMapper {
    @Override
    public UserEntity map(ResultSet resultSet) throws SQLException {
        UserEntity user = new UserEntity();
        user.setId(resultSet.getLong("user_id"));
        user.setName(resultSet.getString("user_name"));
        user.setEmail(resultSet.getString("email"));
        return user;
    }
}

