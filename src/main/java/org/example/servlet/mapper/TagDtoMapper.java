package org.example.servlet.mapper;

import org.example.model.TagEntity;
import org.example.servlet.dto.TagDto;

import java.util.List;

public interface TagDtoMapper {
    TagDto toDto(TagEntity tag);

    TagEntity toEntity(TagDto dto);

    List<TagDto> toDtoList(List<TagEntity> tags);

    List<TagEntity> toEntityList(List<TagDto> dtoList);
}
