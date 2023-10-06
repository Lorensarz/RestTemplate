package org.example.repository.impl;

import org.example.db.ConnectionManager;
import org.example.db.MySQLConnection;
import org.example.model.PostEntity;
import org.example.model.TagEntity;
import org.example.repository.TagRepository;
import org.example.repository.mapper.TagResultSetMapper;
import org.example.repository.mapper.TagResultSetMapperImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class TagRepositoryImpl implements TagRepository{
    private final ConnectionManager dataSource = new MySQLConnection();
    private final TagResultSetMapper resultSetMapper = new TagResultSetMapperImpl();

    @Override
    public List<PostEntity> findPostsByTag(TagEntity tagEntity) {
        String query = "SELECT post_id FROM Post_Tag WHERE tag_id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setObject(1, tagEntity.getId());
             ResultSet resultSet = preparedStatement.executeQuery();
            return resultSetMapper.toListPosts(resultSet);
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
