package org.example.servlet.mapper;

import org.example.model.UserEntity;
import org.example.servlet.dto.UserDto;

import java.util.Collection;
import java.util.List;

public interface UserDtoMapper {
    UserDto toDto(UserEntity user);

    UserEntity toEntity(UserDto dto);

    List<UserDto> toDtoList(Collection<UserEntity> users);
}
