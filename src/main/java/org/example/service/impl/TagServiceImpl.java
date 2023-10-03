package org.example.service.impl;

import org.example.db.ConnectionManager;
import org.example.db.MySQLConnection;
import org.example.model.PostEntity;
import org.example.model.TagEntity;
import org.example.repository.TagRepository;
import org.example.repository.impl.TagRepositoryImpl;
import org.example.repository.mapper.TagResultSetMapper;
import org.example.repository.mapper.TagResultSetMapperImpl;
import org.example.service.TagService;

import java.util.List;

public class TagServiceImpl implements TagService {

    private final TagRepository repository;

    public TagServiceImpl(TagRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<TagEntity> findTagsByPost(long postId) {
        return repository.findTagsByPost(postId);
    }

    @Override
    public void addTagToPost(PostEntity post, TagEntity tag) {

    }

    @Override
    public void removeTagFromPost(PostEntity post, TagEntity tag) {

    }
}
