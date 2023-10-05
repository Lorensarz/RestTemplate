package org.example.servlet;

import com.google.gson.Gson;
import org.example.model.UserEntity;
import org.example.service.UserService;
import org.example.service.impl.UserServiceImpl;
import org.example.servlet.dto.UserDto;
import org.example.servlet.mapper.UserDtoMapper;
import org.example.servlet.mapper.UserDtoMapperImpl;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/users")
public class UserServlet extends HttpServlet {

    private transient UserService userService = new UserServiceImpl();
    private transient UserDtoMapper userMapper = new UserDtoMapperImpl();
    private final transient Gson gson = new Gson();


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String id = req.getParameter("id");
        try {
            if (id != null) {
                UserEntity userId = userService.findById(Long.parseLong(id));
                UserDto userDto = userMapper.toDto(userId);
                writeResponse(resp, gson.toJson(userDto));
                resp.setStatus(200);
            } else {
                findAllUsers(resp);
            }
        } catch (IOException e) {
            writeResponse(resp, e.getMessage());
            resp.setStatus(404, "User not found");
        }

//        resp.getWriter().println("Hello");

    }

    private void findAllUsers(HttpServletResponse resp) throws IOException {
        List<UserEntity> users = userService.findAll();
        List<UserDto> userDtos = userMapper.toDtoList(users);
        writeResponse(resp, gson.toJson(userDtos));

    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        UserDto userDto = gson.fromJson(req.getReader(), UserDto.class);
        UserEntity user = userMapper.toEntity(userDto);
        UserDto savedUserDto = userMapper.toDto(user);
        userService.save(user);
        writeResponse(resp, gson.toJson(savedUserDto));
        resp.setStatus(200);

    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        UserDto userDto = gson.fromJson(req.getReader(), UserDto.class);
        UserEntity user = userMapper.toEntity(userDto);
        userService.update(user);
        UserDto updatedUserDto = userMapper.toDto(user);
        writeResponse(resp, gson.toJson(updatedUserDto));
        resp.setStatus(200);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
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
