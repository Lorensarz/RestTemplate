package org.example.repository.impl;

import org.example.db.ConnectionManager;
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

public class TagRepositoryImpl implements TagRepository {
    private ConnectionManager connectionManager;
    private final TagResultSetMapper resultSetMapper = new TagResultSetMapperImpl();

    public TagRepositoryImpl(ConnectionManager connectionManager) {
        this.connectionManager = connectionManager;

    }

    @Override
    public List<TagEntity> findTagsByPostId(PostEntity postEntity) {
        String query = "SELECT t.tag_id, t.tag_name FROM tags t " +
                "INNER JOIN post_tag pt ON t.tag_id = pt.tag_id " +
                "WHERE pt.post_id = ?";
        try {
            Connection connection = connectionManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setLong(1, postEntity.getId());
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSetMapper.map(resultSet);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void addTagToPost(PostEntity post) {
        String query = "INSERT INTO post_tag (post_id, tag_id) VALUES (?, ?)";
        List<TagEntity> tags = post.getTags();

        try {
            Connection connection = connectionManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            for (TagEntity tag : tags) {
                preparedStatement.setLong(1, post.getId());
                preparedStatement.setLong(2, tag.getId());
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void removeTagFromPost(PostEntity post) {
        String query = "DELETE FROM post_tag WHERE post_id = ? AND tag_id = ?";
        try {
            Connection connection = connectionManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setLong(1, post.getId());
            preparedStatement.setLong(2, post.getTags().get(0).getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);

        }
    }

    @Override
    public void updateTagsForPost(PostEntity post) {
        List<TagEntity> tags = post.getTags();
        String query = "UPDATE post_tag SET tag_id = ? WHERE post_id = ?";
        try {
            Connection connection = connectionManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            for (TagEntity tag : tags) {
                preparedStatement.setLong(1, tag.getId());
                preparedStatement.setLong(2, post.getId());
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
