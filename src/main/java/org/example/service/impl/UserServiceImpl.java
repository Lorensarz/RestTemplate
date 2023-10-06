package org.example.service.impl;

import org.example.model.UserEntity;
import org.example.repository.UserRepository;
import org.example.repository.impl.UserRepositoryImpl;
import org.example.service.UserService;
import org.example.servlet.dto.UserDto;
import org.example.servlet.mapper.UserDtoMapper;
import org.example.servlet.mapper.UserDtoMapperImpl;

import java.util.List;

public class UserServiceImpl implements UserService {

    private final UserDtoMapper userDtoMapper = new UserDtoMapperImpl();
    private final UserRepository repository = new UserRepositoryImpl();

    private UserEntity getUserEntity(UserDto userDto) {
        return userDtoMapper.toEntity(userDto);
    }

    @Override
    public UserEntity findById(UserDto userDto) {
        return repository.findById(getUserEntity(userDto));
        
    }

    @Override
    public boolean deleteById(UserDto userDto) {
        return repository.deleteById(getUserEntity(userDto));
    }

    @Override
    public List<UserDto> findAll() {
        return userDtoMapper.toDtoList(repository.findAll());
    }

    @Override
    public void save(UserDto userDto) {
        repository.save(getUserEntity(userDto));
    }

    @Override
    public void update(UserDto userDto) {
        repository.update(getUserEntity(userDto));
    }
}
