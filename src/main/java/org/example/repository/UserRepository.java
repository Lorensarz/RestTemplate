package org.example.repository;

import org.example.model.UserEntity;

import java.util.List;

public interface UserRepository {
    UserEntity findById(UserEntity userEntity);

    boolean deleteById(UserEntity userEntity);

    List<UserEntity> findAll();

    void save(UserEntity user);
    void update(UserEntity user);
}
