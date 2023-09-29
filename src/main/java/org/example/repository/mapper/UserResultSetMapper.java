package org.example.repository.mapper;

import org.example.model.UserEntity;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserResultSetMapper implements SimpleResultSetMapper {
    @Override
    public UserEntity map(ResultSet resultSet) throws SQLException {
        UserEntity user = new UserEntity();
        user.setId(resultSet.getLong("id"));
        user.setName(resultSet.getString("username"));
        user.setEmail(resultSet.getString("email"));
        return user;
    }
}

