package org.example.servlet.mapper;

import org.example.model.UserEntity;
import org.example.servlet.dto.UserDto;

public interface UserDtoMapper {
    UserDto toDto(UserEntity user);

    UserEntity toEntity(UserDto dto);
}
