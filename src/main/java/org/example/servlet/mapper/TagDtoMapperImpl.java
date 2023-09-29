package org.example.servlet.mapper;

import org.example.model.TagEntity;
import org.example.servlet.dto.TagDto;

import java.util.ArrayList;
import java.util.List;

public class TagDtoMapperImpl implements TagDtoMapper {

    @Override
    public TagDto toDto(TagEntity tag) {
        TagDto dto = new TagDto();
        dto.setId(tag.getId());
        dto.setName(tag.getName());
        return dto;
    }

    @Override
    public TagEntity toEntity(TagDto dto) {
        TagEntity tag = new TagEntity();
        tag.setId(dto.getId());
        tag.setName(dto.getName());
        return tag;
    }

    @Override
    public List<TagDto> toDtoList(List<TagEntity> tags) {
        List<TagDto> dtoList = new ArrayList<>();
        for (TagEntity tag : tags) {
            dtoList.add(toDto(tag));
        }
        return dtoList;
    }

    @Override
    public List<TagEntity> toEntityList(List<TagDto> dtoList) {
        List<TagEntity> tags = new ArrayList<>();
        for (TagDto dto : dtoList) {
            tags.add(toEntity(dto));
        }
        return tags;
    }
}
