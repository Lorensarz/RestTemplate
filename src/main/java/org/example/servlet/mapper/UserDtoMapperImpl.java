package org.example.servlet.mapper;

import org.example.model.UserEntity;
import org.example.servlet.dto.UserDto;

public class UserDtoMapperImpl implements UserDtoMapper {
    private PostDtoMapperImpl postMapper = new PostDtoMapperImpl();

    @Override
    public UserDto toDto(UserEntity user) {
        UserDto dto = new UserDto();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setPosts(postMapper.toDtoList(user.getPosts()));
        return dto;
    }

    @Override
    public UserEntity toEntity(UserDto dto) {
        UserEntity user = new UserEntity();
        user.setId(dto.getId());
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setPosts(postMapper.toEntityList(dto.getPosts()));
        return user;
    }
}