package org.example.service.impl;

import org.example.model.UserEntity;
import org.example.repository.UserRepository;
import org.example.service.UserService;
import org.example.servlet.dto.UserDto;
import org.example.servlet.mapper.UserDtoMapper;

import java.util.List;

public class UserServiceImpl implements UserService {

    private final UserDtoMapper userDtoMapper;
    private final UserRepository repository;

    public UserServiceImpl(UserDtoMapper userDtoMapper, UserRepository repository) {
        this.userDtoMapper = userDtoMapper;
        this.repository = repository;
    }

    private UserEntity getUserEntity(UserDto userDto) {
        return userDtoMapper.toEntity(userDto);
    }

    @Override
    public UserEntity findById(long id) {
        return repository.findById(id);
        
    }

    @Override
    public boolean deleteById(long id) {
        return repository.deleteById(id);
    }

    @Override
    public List<UserDto> findAll() {
        return userDtoMapper.toDtoList(repository.findAll());
    }

    @Override
    public void save(UserDto userDto) {
        repository.save(userDtoMapper.toEntity(userDto));
    }

    @Override
    public void update(UserDto userDto) {
        repository.update(userDtoMapper.toEntity(userDto));
    }
}
