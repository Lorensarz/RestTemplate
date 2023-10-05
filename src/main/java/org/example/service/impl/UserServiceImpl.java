package org.example.service.impl;

import org.example.model.UserEntity;
import org.example.repository.UserRepository;
import org.example.repository.impl.UserRepositoryImpl;
import org.example.service.UserService;

import java.util.List;

public class UserServiceImpl implements UserService {

    private final UserRepository repository = new UserRepositoryImpl();

    @Override
    public UserEntity findById(long id) {
        return repository.findById(id);
        
    }

    @Override
    public boolean deleteById(long id) {
        return repository.deleteById(id);
    }

    @Override
    public List<UserEntity> findAll() {
        return repository.findAll();
    }

    @Override
    public void save(UserEntity user) {
        repository.save(user);
    }

    @Override
    public void update(UserEntity user) {
        repository.update(user);
    }
}
