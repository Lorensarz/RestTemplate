package org.example.servlet;

import com.google.gson.Gson;

import org.example.exceptions.NotFoundException;
import org.example.model.UserEntity;
import org.example.service.impl.UserServiceImpl;
import org.example.servlet.dto.UserDto;
import org.example.servlet.mapper.UserDtoMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "Users", urlPatterns = {"/users"})
public class UserServlet extends HttpServlet {

    private final transient UserServiceImpl userService;
    private final transient UserDtoMapper userMapper;
    private final transient Gson gson = new Gson();

    public UserServlet(UserServiceImpl userService, UserDtoMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        UserEntity userId = userService.findById(Long.parseLong(id));
        if (userId != null) {
            UserDto userDto = userMapper.toDto(userId);
            writeResponse(resp, gson.toJson(userDto));
            resp.setStatus(200);

        } else {
            throw new NotFoundException("User не найден");
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserDto userDto = gson.fromJson(req.getReader(), UserDto.class);
        UserEntity user = userMapper.toEntity(userDto);
        UserDto savedUserDto = userMapper.toDto(user);
        userService.save(user);
        writeResponse(resp, gson.toJson(savedUserDto));
        resp.setStatus(200);

    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserDto userDto = gson.fromJson(req.getReader(), UserDto.class);
        UserEntity user = userMapper.toEntity(userDto);
        userService.update(user);
        UserDto updatedUserDto = userMapper.toDto(user);
        writeResponse(resp, gson.toJson(updatedUserDto));
        resp.setStatus(200);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        userService.deleteById(Long.parseLong(id));
        resp.setStatus(200);
    }

    private void writeResponse(HttpServletResponse resp, String jsonString) throws IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        PrintWriter out = resp.getWriter();
        out.print(jsonString);
        out.flush();
    }

}
