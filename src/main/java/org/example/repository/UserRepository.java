package org.example.repository;

import org.example.model.UserEntity;

import java.util.List;

public interface UserRepository {
    UserEntity findById(long id);

    boolean deleteById(long id);

    List<UserEntity> findAll();

    boolean save(UserEntity user);

    void update(UserEntity user);
}
