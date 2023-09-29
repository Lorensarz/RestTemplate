package org.example.repository.impl;

import org.example.model.PostEntity;
import org.example.model.TagEntity;
import org.example.repository.PostRepository;
import org.example.repository.mapper.PostResultSetMapper;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class PostRepositoryImpl implements PostRepository {

    private final DataSource dataSource;
    private final PostResultSetMapper resultSetMapper;

    public PostRepositoryImpl(DataSource dataSource, PostResultSetMapper resultSetMapper) {
        this.dataSource = dataSource;
        this.resultSetMapper = resultSetMapper;
    }

    @Override
    public PostEntity findById(long id) {
        String query = "SELECT id, content, title FROM posts WHERE id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setObject(1, id);
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
    public void delete(long id) {
        String query = "DELETE FROM posts WHERE id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setObject(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public List<TagEntity> findTagsByPost(long postId) {
        String query = "SELECT tag_id FROM Post_Tag WHERE post_id = ?";
        try (Connection connection = dataSource.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setObject(1, postId);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSetMapper.toListTags(resultSet);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void addTagToPost(PostEntity post, TagEntity tag) {
        String query = "INSERT INTO post_tag (post_id, tag_id) VALUES (?, ?)";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setObject(1, post.getId());
            preparedStatement.setObject(2, tag.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void removeTagFromPost(PostEntity post, TagEntity tag) {
        String query = "DELETE FROM post_tag WHERE post_id = ? AND tag_id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setObject(1, post.getId());
            preparedStatement.setObject(2, tag.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
