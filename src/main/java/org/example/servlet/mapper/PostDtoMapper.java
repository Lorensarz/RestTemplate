package org.example.servlet.mapper;

import org.example.model.PostEntity;
import org.example.servlet.dto.PostDto;

import java.util.List;

public interface PostDtoMapper {
    PostDto toDto(PostEntity post);

    PostEntity toEntity(PostDto dto);

    List<PostDto> toDtoList(List<PostEntity> posts);

    List<PostEntity> toEntityList(List<PostDto> dtoList);
}
