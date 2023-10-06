package org.example.repository.impl;

import org.example.db.ConnectionManager;
import org.example.db.MySQLConnection;
import org.example.model.UserEntity;
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

    private final ConnectionManager dataSource = new MySQLConnection();


    @Override
    public UserEntity findById(UserEntity userEntity) {
        String query = "SELECT * FROM users WHERE id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement =
                     connection.prepareStatement(query)) {

            preparedStatement.setObject(1, userEntity.getId());
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSetMapper.map(resultSet);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        throw new IllegalStateException("Could not find user for id " + userEntity.getId());
    }

    @Override
    public boolean deleteById(UserEntity userEntity) {
        String query = "DELETE FROM users WHERE id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setObject(1, userEntity.getId());
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<UserEntity> findAll() {
        String query = "SELECT * FROM users";
        List<UserEntity> users = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
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
    public void save(UserEntity user) {
        String query = "INSERT INTO users (name, email) VALUES (?, ?)";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getEmail());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(UserEntity user) {
        String query = "UPDATE users SET name = ?, email = ? WHERE id = ?";
        try (Connection connection = dataSource.getConnection();
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
