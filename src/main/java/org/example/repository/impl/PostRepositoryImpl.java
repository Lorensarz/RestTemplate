package org.example.repository.impl;

import org.example.db.ConnectionManager;
import org.example.model.PostEntity;
import org.example.model.TagEntity;
import org.example.repository.PostRepository;
import org.example.repository.TagRepository;
import org.example.repository.mapper.PostResultSetMapper;
import org.example.repository.mapper.PostResultSetMapperImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PostRepositoryImpl implements PostRepository {

    private final ConnectionManager connectionManager;
    private final PostResultSetMapper postResultSetMapper = new PostResultSetMapperImpl();

    private TagRepository tagRepository;

    public PostRepositoryImpl(ConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }


    @Override
    public List<PostEntity> findPostsByUserId(long userId) {
        String query = "SELECT * FROM posts WHERE user_id = ?";
        List<PostEntity> posts = new ArrayList<>();

        try {
            Connection connection = connectionManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setLong(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            tagRepository = new TagRepositoryImpl(connectionManager);

            while (resultSet.next()) {
                PostEntity post = postResultSetMapper.map(resultSet);
                List<TagEntity> tagEntities = tagRepository.findTagsByPostId(post);
                post.setTags(tagEntities);
                posts.add(post);
            }

            return posts;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            connectionManager.close();
        }
    }

    @Override
    public List<PostEntity> findAll() {
        String query = "SELECT * FROM posts p";
        List<PostEntity> posts = new ArrayList<>();
        tagRepository = new TagRepositoryImpl(connectionManager);

        try {
            Connection connection = connectionManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                PostEntity post = postResultSetMapper.map(resultSet);
                posts.add(post);
            }

            for (PostEntity post : posts) {
                List<TagEntity> tagEntities = tagRepository.findTagsByPostId(post);
                post.setTags(tagEntities);
            }

            return posts;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            connectionManager.close();
        }
    }

    @Override
    public void save(PostEntity post) {
        String query = "INSERT INTO posts (content, title, user_id) VALUES (?, ?, ?)";

        tagRepository = new TagRepositoryImpl(connectionManager);

        try {
            Connection connection = connectionManager.getConnection();

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, post.getContent());
            preparedStatement.setString(2, post.getTitle());
            preparedStatement.setLong(3, post.getUserId());
            preparedStatement.executeUpdate();
            tagRepository.addTagToPost(post);


        } catch (SQLException e) {
            throw new RuntimeException(e);

        } finally {
            connectionManager.close();
        }
    }

    @Override
    public void update(PostEntity post) {
        String query = "UPDATE posts SET content = ?, title = ?, user_id = ? WHERE post_id = ?";

        tagRepository = new TagRepositoryImpl(connectionManager);

        try {
            Connection connection = connectionManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, post.getContent());
            preparedStatement.setString(2, post.getTitle());
            preparedStatement.setLong(3, post.getUserId());
            preparedStatement.setLong(4, post.getId());
            preparedStatement.executeUpdate();
            tagRepository.updateTagsForPost(post);
        } catch (SQLException e) {
            throw new RuntimeException(e);

        } finally {
            connectionManager.close();
        }

    }

    @Override
    public List<PostEntity> findPostsByTag(TagEntity tagEntity) {
        String query = "SELECT post_id FROM post_tag WHERE tag_id = ?";
        tagRepository = new TagRepositoryImpl(connectionManager);
        List<PostEntity> posts = new ArrayList<>();
        List<Long> postsId = new ArrayList<>();

        try {
            Connection connection = connectionManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setObject(1, tagEntity.getId());
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                postsId.add(resultSet.getLong("post_id"));
            }
            for (Long postId : postsId) {
                PostEntity post = findPostByPostId(postId);
                if (post != null) {
                    post.setTags(tagRepository.findTagsByPostId(post));
                }
                posts.add(post);
            }
            return posts;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public PostEntity findPostByPostId(Long postId) {
        String query = "SELECT * FROM posts WHERE post_id = ?";
        tagRepository = new TagRepositoryImpl(connectionManager);
        PostEntity post = null;
        try {
            Connection connection = connectionManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setLong(1, postId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                post = postResultSetMapper.map(resultSet);
                post.setTags(tagRepository.findTagsByPostId(post));
                return post;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return post;
    }


    @Override
    public void delete(long id) {
        String query = "DELETE FROM posts WHERE post_id = ?";
        try {
            Connection connection = connectionManager.getConnection();

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setObject(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
