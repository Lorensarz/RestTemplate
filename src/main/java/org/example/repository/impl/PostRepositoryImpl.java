package org.example.repository.impl;

import org.example.db.ConnectionManager;
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
import java.util.ArrayList;
import java.util.List;

public class PostRepositoryImpl implements PostRepository {

    private final PostResultSetMapper postResultSetMapper = new PostResultSetMapperImpl();
    private final TagResultSetMapper tagResultSetMapper = new TagResultSetMapperImpl();
    private final ConnectionManager connectionManager;

    public PostRepositoryImpl(ConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }

    @Override
    public List<PostEntity> findPostsByUserId(long userId) {
        String query = "SELECT * FROM posts WHERE user_id = ?";
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();

            List<PostEntity> posts = new ArrayList<>();
            while (resultSet.next()) {
                PostEntity post = postResultSetMapper.map(resultSet);
                post.setTags(findTagsForPost(connection, post.getId())); // Call a helper method to find tags
                posts.add(post);
            }

            return posts;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Helper method to find tags for a post
    private List<TagEntity> findTagsForPost(Connection connection, long postId) {
        String tagQuery = "SELECT t.name FROM tags t " +
                "JOIN post_tag pt ON t.id = pt.tag_id " +
                "WHERE pt.post_id = ?";
        try (PreparedStatement tagStatement = connection.prepareStatement(tagQuery)) {
            tagStatement.setLong(1, postId);
            ResultSet tagResultSet = tagStatement.executeQuery();

            List<TagEntity> tags = new ArrayList<>();
            while (tagResultSet.next()) {
                TagEntity tag = tagResultSetMapper.map(tagResultSet);
                tags.add(tag);
            }

            return tags;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public List<PostEntity> findAll() {
        String query = "SELECT id, content, title FROM posts";
        try (Connection connection = connectionManager.getConnection();
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
        try (Connection connection = connectionManager.getConnection();
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
        try (Connection connection = connectionManager.getConnection();
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
        try (Connection connection = connectionManager.getConnection();
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
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setObject(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
