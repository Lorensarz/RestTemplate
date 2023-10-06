package org.example.repository.impl;

import org.example.db.ConnectionManager;
import org.example.db.MySQLConnection;
import org.example.model.PostEntity;
import org.example.model.TagEntity;
import org.example.repository.PostRepository;
import org.example.repository.mapper.PostResultSetMapper;
import org.example.repository.mapper.PostResultSetMapperImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class PostRepositoryImpl implements PostRepository {

    private final PostResultSetMapper resultSetMapper = new PostResultSetMapperImpl();
    private final ConnectionManager dataSource = new MySQLConnection();

    @Override
    public PostEntity findById(PostEntity postEntity) {
        String query = "SELECT id, content, title FROM posts WHERE id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setObject(1, postEntity.getId());
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSetMapper.map(resultSet);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public List<PostEntity> findAll() {
        String query = "SELECT id, content, title FROM posts";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSetMapper.toListPosts(resultSet);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void save(PostEntity post) {
        String query = "INSERT INTO posts (content, title) VALUES (?, ?)";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, post.getContent());
            preparedStatement.setString(2, post.getTitle());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(PostEntity post) {
        String query = "UPDATE posts SET content = ?, title = ? WHERE id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, post.getContent());
            preparedStatement.setString(2, post.getTitle());
            preparedStatement.setObject(3, post.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public List<TagEntity> findTagsByPostId(PostEntity postEntity) {
        String query = "SELECT t.id, t.name FROM tags t " +
                "INNER JOIN post_tag pt ON t.id = pt.tag_id " +
                "WHERE pt.post_id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, postEntity.getId());
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSetMapper.toListTags(resultSet);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public void delete(PostEntity postEntity) {
        String query = "DELETE FROM posts WHERE id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setObject(1, postEntity.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
