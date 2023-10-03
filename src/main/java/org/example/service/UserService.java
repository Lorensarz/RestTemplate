package org.example.service;

import org.example.model.UserEntity;

import java.util.List;

public interface UserService {

    UserEntity findById(long id);
    boolean deleteById(long id);

    List<UserEntity> findAll();

    void save(UserEntity user);
    void update(UserEntity user);


}
