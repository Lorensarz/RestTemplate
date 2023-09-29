package org.example.repository.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface SimpleResultSetMapper {
    <T> T map(ResultSet resultSet) throws SQLException;

}
