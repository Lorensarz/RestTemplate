package org.example.service;

import org.example.model.UserEntity;
import org.example.servlet.dto.UserDto;

import java.util.List;

public interface UserService {

    UserEntity findById(long id);
    boolean deleteById(long id);

    List<UserDto> findAll();

    void save(UserDto userDto);
    void update(UserDto userDto);


}
