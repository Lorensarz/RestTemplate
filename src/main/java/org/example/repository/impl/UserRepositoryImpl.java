package org.example.repository.impl;

import org.example.model.UserEntity;
import org.example.repository.UserRepository;
import org.example.repository.mapper.UserResultSetMapper;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserRepositoryImpl implements UserRepository {
    private final UserResultSetMapper resultSetMapper;

    private final DataSource dataSource;

    public UserRepositoryImpl(UserResultSetMapper resultSetMapper, DataSource dataSource) {
        this.resultSetMapper = resultSetMapper;
        this.dataSource = dataSource;
    }

    @Override
    public UserEntity findById(java.util.UUID uuid) {
        String query = "SELECT * FROM users WHERE uuid = ?";
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement preparedStatement =
                    connection.prepareStatement(query);
            preparedStatement.setObject(1, uuid);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSetMapper.map(resultSet);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        throw new IllegalStateException("Could not find user for UUID");
    }

    @Override
    public boolean deleteById(java.util.UUID uuid) {
        String query = "DELETE FROM users WHERE uuid = ?";
        try (Connection connection = dataSource.getConnection()) {
            int affectedRows;
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setObject(1, uuid);
                affectedRows = preparedStatement.executeUpdate();
            }
            return affectedRows > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<UserEntity> findAll() {
        List<UserEntity> aUsers = new ArrayList<>();
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement preparedStatement =
                    connection.prepareStatement("SELECT * FROM users");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                aUsers.add(resultSetMapper.map(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return aUsers;
    }

    @Override
    public void save(UserEntity user) {
        String query = "INSERT INTO users (name, email) VALUES (?, ?)";
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement preparedStatement =
                    connection.prepareStatement(query);
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getEmail());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(UserEntity user) {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement preparedStatement =
                    connection.prepareStatement("UPDATE users SET userName = ?, email = ? WHERE uuid = ?");
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getEmail());
            preparedStatement.setObject(3, user.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
