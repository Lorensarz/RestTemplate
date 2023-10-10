package org.example.servlet.mapper;

import org.example.model.UserEntity;
import org.example.servlet.dto.UserDto;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class UserDtoMapperImpl implements UserDtoMapper {

    @Override
    public UserDto toDto(UserEntity user) {
        UserDto dto = new UserDto();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        return dto;
    }

    @Override
    public UserEntity toEntity(UserDto dto) {
        UserEntity user = new UserEntity();
        user.setId(dto.getId());
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        return user;
    }

    @Override
    public List<UserDto> toDtoList(Collection<UserEntity> users) {
        List<UserDto> usersDto = new ArrayList<>();
        for (UserEntity user : users) {
            usersDto.add(toDto(user));
        }
        return usersDto;
    }

}
