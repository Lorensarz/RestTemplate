package org.example.repository.impl;

import org.example.db.ConnectionManager;
import org.example.model.UserEntity;
import org.example.repository.PostRepository;
import org.example.repository.TagRepository;
import org.example.repository.UserRepository;
import org.example.repository.mapper.UserResultSetMapper;
import org.example.repository.mapper.UserResultSetMapperImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserRepositoryImpl implements UserRepository {
    private final UserResultSetMapper resultSetMapper = new UserResultSetMapperImpl();
    private final ConnectionManager connectionManager;
    private PostRepository postRepository;
    private TagRepository tagRepository;

    public UserRepositoryImpl(ConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }

    @Override
    public UserEntity findById(long id) {
        String query = "SELECT * FROM users WHERE id = ?";
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setObject(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSetMapper.map(resultSet);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        throw new IllegalStateException("Could not find user for id " + id);
    }

    @Override
    public boolean deleteById(long userId) {
        String deleteUserQuery = "DELETE FROM users WHERE id = ?";
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement deleteUserStatement = connection.prepareStatement(deleteUserQuery)) {
            deleteUserStatement.setLong(1, userId);
            int userDeletedCount = deleteUserStatement.executeUpdate();
            return userDeletedCount == 1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public List<UserEntity> findAll() {
        String query = "SELECT * FROM users";
        List<UserEntity> users = new ArrayList<>();
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                users.add(resultSetMapper.map(resultSet));
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Could not find all users: " + e.getMessage(), e);
        }
        return users;
    }

    @Override
    public boolean save(UserEntity user) {
        String query = "INSERT INTO users (name, email) VALUES (?, ?)";
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getEmail());
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(UserEntity user) {
        String query = "UPDATE users SET name = ?, email = ? WHERE id = ?";
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getEmail());
            preparedStatement.setObject(3, user.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
