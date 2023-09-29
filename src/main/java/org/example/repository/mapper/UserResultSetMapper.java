package org.example.repository.mapper;

import org.example.model.UserEntity;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserResultSetMapper implements SimpleResultSetMapper {
    public UserEntity map(ResultSet resultSet) throws SQLException {
        UserEntity User = new UserEntity();
        User.setId(resultSet.getLong("id"));
        User.setName(resultSet.getString("username"));
        User.setEmail(resultSet.getString("email"));
        return User;
    }
}

