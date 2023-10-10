package org.example.repository.impl;

import org.example.db.ConnectionManager;
import org.example.db.MySQLConnection;
import org.example.model.PostEntity;
import org.example.model.TagEntity;
import org.example.repository.PostRepository;
import org.example.repository.mapper.PostResultSetMapper;
import org.example.repository.mapper.PostResultSetMapperImpl;
import org.example.repository.mapper.TagResultSetMapper;
import org.example.repository.mapper.TagResultSetMapperImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class PostRepositoryImpl implements PostRepository {

    private final PostResultSetMapper postResultSetMapper = new PostResultSetMapperImpl();
    private final TagResultSetMapper tagResultSetMapper = new TagResultSetMapperImpl();
    private final ConnectionManager dataSource = new MySQLConnection();

    @Override
    public PostEntity findById(long id) {
        String query = "SELECT * FROM posts WHERE id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setObject(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                PostEntity post = postResultSetMapper.map(resultSet);
                String tagQuery = "SELECT t.name FROM tags t " +
                        "JOIN post_tag pt ON t.id = pt.tag_id " +
                        "WHERE pt.post_id = ?";
                try (PreparedStatement tagStatement = connection.prepareStatement(tagQuery)) {
                    tagStatement.setLong(1, id);
                    ResultSet tagResultSet = tagStatement.executeQuery();
                    List<TagEntity> tags = tagResultSetMapper.toListTags(tagResultSet);
                    post.setTags(tags);
                }
                return post;
            } else {
                return null;
            }
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
            return postResultSetMapper.toListPosts(resultSet);
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
        String query = "UPDATE posts SET content = ?, title = ?, user_id = ? WHERE id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, post.getContent());
            preparedStatement.setString(2, post.getTitle());
            preparedStatement.setObject(3, post.getUserId());
            preparedStatement.setObject(4, post.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public List<PostEntity> findPostsByTag(TagEntity tagEntity) {
        String query = "SELECT post_id FROM Post_Tag WHERE tag_id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setObject(1, tagEntity.getId());
            ResultSet resultSet = preparedStatement.executeQuery();
            return postResultSetMapper.toListPosts(resultSet);
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
}
