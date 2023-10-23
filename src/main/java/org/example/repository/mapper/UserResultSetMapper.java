package org.example.repository.mapper;

import org.example.model.UserEntity;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface UserResultSetMapper {
    UserEntity map(ResultSet resultSet) throws SQLException;
}
