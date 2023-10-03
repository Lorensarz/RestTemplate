package org.example.service.impl;

import org.example.db.ConnectionManager;
import org.example.db.MySQLConnection;
import org.example.model.PostEntity;
import org.example.repository.PostRepository;
import org.example.repository.impl.PostRepositoryImpl;
import org.example.repository.mapper.PostResultSetMapper;
import org.example.repository.mapper.PostResultSetMapperImpl;
import org.example.repository.mapper.SimpleResultSetMapper;
import org.example.service.PostService;

import java.util.List;

public class PostServiceImpl implements PostService {
    private final PostRepository repository;

    public PostServiceImpl(PostRepository repository) {
        this.repository = repository;
    }

    @Override
    public PostEntity findById(long id) {
        return repository.findById(id);
    }

    @Override
    public List<PostEntity> findAll() {
        return repository.findAll();
    }

    @Override
    public void save(PostEntity post) {
        repository.save(post);
    }

    @Override
    public void update(PostEntity post) {
        repository.update(post);
    }

    @Override
    public void delete(long id) {
        repository.delete(id);
    }
}
