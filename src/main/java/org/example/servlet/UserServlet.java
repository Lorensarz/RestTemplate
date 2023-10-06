package org.example.servlet;

import com.google.gson.Gson;
import org.example.service.UserService;
import org.example.service.impl.UserServiceImpl;
import org.example.servlet.dto.UserDto;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/users")
public class UserServlet extends HttpServlet {

    private final UserService userService = new UserServiceImpl();
    private final Gson gson = new Gson();


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String id = req.getParameter("id");
        if (id != null) {
            UserDto userDto = gson.fromJson(req.getReader(), UserDto.class);
            userService.findById(userDto);
            writeResponse(resp, gson.toJson(userDto));
        } else {
            findAllUsers(resp);
        }
//        resp.getWriter().println("Hello");
    }

    private void findAllUsers(HttpServletResponse resp) throws IOException {
        writeResponse(resp, gson.toJson(userService.findAll()));
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        UserDto userDto = gson.fromJson(req.getReader(), UserDto.class);
        userService.save(userDto);
        resp.setStatus(200);

    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        UserDto userDto = gson.fromJson(req.getReader(), UserDto.class);
        userService.update(userDto);
        resp.setStatus(200);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        UserDto userDto = gson.fromJson(req.getReader(), UserDto.class);
        userService.deleteById(userDto);
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
