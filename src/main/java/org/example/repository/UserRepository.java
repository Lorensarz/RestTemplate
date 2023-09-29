package org.example.repository;

import org.example.model.UserEntity;

import java.util.List;

public interface UserRepository {
    UserEntity findById(java.util.UUID uuid);

    boolean deleteById(java.util.UUID uuid);

    List<UserEntity> findAll();

    void save(UserEntity User);
    void update(UserEntity User);
}
