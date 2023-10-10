package org.example.servlet;

import com.google.gson.Gson;
import org.example.model.UserEntity;
import org.example.service.UserService;
import org.example.servlet.dto.UserDto;
import org.example.servlet.mapper.UserDtoMapper;
import org.example.servlet.mapper.UserDtoMapperImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class UserServletTest {

    @Mock
    private UserService userService;
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private RequestDispatcher dispatcher;
    @InjectMocks
    private UserServlet userServlet;
    private final UserDtoMapper userDtoMapper = new UserDtoMapperImpl();
    private final Gson gson = new Gson();

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        when(request.getRequestDispatcher(anyString())).thenReturn(dispatcher);
    }


    @Test
    public void testDoGet() throws ServletException, IOException {
        UserDto userDto = new UserDto(1L, "Test User", "test@example.com");
        UserEntity userEntity = userDtoMapper.toEntity(userDto);

        when(request.getParameter("id")).thenReturn("1");
        when(userService.findById(anyLong())).thenReturn(userEntity);

        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);

        userServlet.doGet(request, response);

        verify(response).setContentType("application/json");
        verify(response).setCharacterEncoding("UTF-8");

        String expectedJson = gson.toJson(userDto);
        writer.flush();
        assertEquals(expectedJson, stringWriter.toString());
    }


    @Test
    public void testDoGetWithoutId() throws ServletException, IOException {
        List<UserDto> users = new ArrayList<>();
        users.add(new UserDto(3L, "new user", "new@site"));
        users.add(new UserDto(4L, "New User", "newuser@example.com"));

        when(request.getParameter("id")).thenReturn(null);
        when(userService.findAll()).thenReturn(users);

        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);

        userServlet.doGet(request, response);

        verify(response).setContentType("application/json");
        verify(response).setCharacterEncoding("UTF-8");

        String expectedJson = gson.toJson(users);
        writer.flush();
        assertEquals(expectedJson, stringWriter.toString());
    }

    @Test
    public void testDoPost() throws ServletException, IOException {
        String json = "{\"id\": 6, \"name\": \"Jay\", \"email\": \"jay@example.com\"}";
        BufferedReader reader = new BufferedReader(new StringReader(json));
        when(request.getReader()).thenReturn(reader);

        userServlet.doPost(request, response);

        verify(userService).save(any(UserDto.class));
    }

    @Test
    public void testDoPut() throws IOException {
        String json = "{\"id\": 1, \"name\": \"Updated User\", \"email\": \"updated@example.com\"}";
        BufferedReader reader = new BufferedReader(new StringReader(json));
        when(request.getReader()).thenReturn(reader);

        userServlet.doPut(request, response);

        verify(userService).update(any(UserDto.class));
    }


    @Test
    public void testDoDelete() throws IOException {
        String id = "1";
        when(request.getParameter("id")).thenReturn(id);
        userServlet.doDelete(request, response);
        verify(userService).deleteById(Long.parseLong(id));
    }
}
