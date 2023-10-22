package org.example.servlet.mapper;

import org.example.model.UserEntity;
import org.example.servlet.dto.UserDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


public class UserDtoMapperImplTest {

    private UserDtoMapperImpl userDtoMapper;

    @BeforeEach
    public void setUp() {
        userDtoMapper = new UserDtoMapperImpl();
    }

    @Test
    public void testToDto() {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(1L);
        userEntity.setName("Test User");
        userEntity.setEmail("test@example.com");

        UserDto userDto = userDtoMapper.toDto(userEntity);

        assertNotNull(userDto);
        assertEquals(userEntity.getId(), userDto.getId());
        assertEquals(userEntity.getName(), userDto.getName());
        assertEquals(userEntity.getEmail(), userDto.getEmail());
    }

    @Test
    public void testToEntity() {
        UserDto userDto = new UserDto();
        userDto.setId(1L);
        userDto.setName("Test User");
        userDto.setEmail("test@example.com");

        UserEntity userEntity = userDtoMapper.toEntity(userDto);

        assertNotNull(userEntity);
        assertEquals(userDto.getId(), userEntity.getId());
        assertEquals(userDto.getName(), userEntity.getName());
        assertEquals(userDto.getEmail(), userEntity.getEmail());
    }

    @Test
    public void testToDtoList() {
        List<UserEntity> userEntities = new ArrayList<>();
        for (int i = 1; i <= 3; i++) {
            UserEntity userEntity = new UserEntity();
            userEntity.setId(i);
            userEntity.setName("Test User " + i);
            userEntity.setEmail("test" + i + "@example.com");
            userEntities.add(userEntity);
        }

        List<UserDto> userDtos = userDtoMapper.toDtoList(userEntities);

        assertNotNull(userDtos);
        assertEquals(userEntities.size(), userDtos.size());

        for (int i = 0; i < userEntities.size(); i++) {
            assertEquals(userEntities.get(i).getId(), userDtos.get(i).getId());
            assertEquals(userEntities.get(i).getName(), userDtos.get(i).getName());
            assertEquals(userEntities.get(i).getEmail(), userDtos.get(i).getEmail());
        }
    }
}
