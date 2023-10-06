package org.example.service;

import org.example.model.UserEntity;
import org.example.servlet.dto.UserDto;

import java.util.List;

public interface UserService {

    UserEntity findById(UserDto userDto);
    boolean deleteById(UserDto userDto);

    List<UserDto> findAll();

    void save(UserDto userDto);
    void update(UserDto userDto);


}
