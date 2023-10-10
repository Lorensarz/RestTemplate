package org.example.servlet;

import com.google.gson.Gson;
import org.example.repository.UserRepository;
import org.example.repository.impl.UserRepositoryImpl;
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

@WebServlet("/users")
public class UserServlet extends HttpServlet {

    private final UserDtoMapper userDtoMapper;
    private final UserRepository userRepository;

    UserService userService = new  UserServiceImpl(
            userDtoMapper = new UserDtoMapperImpl(),
            userRepository = new UserRepositoryImpl());
    private final Gson gson = new Gson();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String id = req.getParameter("id");
        if (id != null) {
            UserDto userDto = userDtoMapper.toDto(userService.findById(Long.parseLong(id)));
            writeResponse(resp, gson.toJson(userDto));
        } else {
            findAllUsers(resp);
        }
    }

    private void findAllUsers(HttpServletResponse resp) throws IOException {
        writeResponse(resp, gson.toJson(userService.findAll()));
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        UserDto userDto = gson.fromJson(req.getReader(), UserDto.class);
        userService.save(userDto);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        UserDto userDto = gson.fromJson(req.getReader(), UserDto.class);
        userService.update(userDto);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String id = req.getParameter("id");
        userService.deleteById(Long.parseLong(id));
    }

    private void writeResponse(HttpServletResponse resp, String jsonString) throws IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        PrintWriter out = resp.getWriter();
        out.print(jsonString);
        out.flush();
    }

}
