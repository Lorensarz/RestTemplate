package org.example.service.impl;

import org.example.model.UserEntity;
import org.example.repository.UserRepository;
import org.example.servlet.dto.UserDto;
import org.example.servlet.mapper.UserDtoMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class UserServiceImplTest {

    @Mock
    private UserRepository repository;
    @Mock
    private UserDtoMapper userDtoMapper;

    private UserServiceImpl userService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        userService = new UserServiceImpl(userDtoMapper, repository);
    }

    @Test
    public void testFindById() {
        long userId = 1L;
        UserEntity expectedUser = new UserEntity();
        expectedUser.setId(userId);
        expectedUser.setName("TestUser");

        when(repository.findById(userId)).thenReturn(expectedUser);

        UserEntity actualUser = userService.findById(userId);

        verify(repository).findById(userId);

        assertNotNull(actualUser);
        assertEquals(userId, actualUser.getId());
        assertEquals("TestUser", actualUser.getName());
    }

    @Test
    public void testDeleteById() {
        long userId = 1L;
        when(repository.deleteById(userId)).thenReturn(true);
        boolean result = userService.deleteById(userId);

        verify(repository).deleteById(userId);

        assertTrue(result);
    }

    @Test
    public void testFindAll() {
        List<UserEntity> userEntities = new ArrayList<>();
        List<UserDto> userDtos = new ArrayList<>();

        when(repository.findAll()).thenReturn(userEntities);
        when(userDtoMapper.toDtoList(userEntities)).thenReturn(userDtos);

        List<UserDto> result = userService.findAll();

        verify(repository).findAll();
        verify(userDtoMapper).toDtoList(userEntities);

        assertNotNull(result);
        assertEquals(result, userDtos);
    }

    @Test
    public void testSave() {
        UserDto userDto = new UserDto();
        UserEntity userEntity = new UserEntity();

        when(userDtoMapper.toEntity(userDto)).thenReturn(userEntity);

        userService.save(userDto);

        verify(userDtoMapper).toEntity(userDto);
        verify(repository).save(userEntity);
    }

    @Test
    public void testUpdate() {
        UserDto userDto = new UserDto();
        UserEntity userEntity = new UserEntity();

        when(userDtoMapper.toEntity(userDto)).thenReturn(userEntity);

        userService.update(userDto);

        verify(userDtoMapper).toEntity(userDto);
        verify(repository).update(userEntity);
    }
}
