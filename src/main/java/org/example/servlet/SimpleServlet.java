package org.example.servlet;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet("/")
public class SimpleServlet extends HttpServlet {



    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        UUID uuid = UUID.randomUUID();// Our Id from request
//        SimpleEntity byId = service.findById(uuid);
//        OutGoingDto outGoingDto = dtomapper.map(byId);
        // return our DTO
        resp.getWriter().println("Hello");
    }
//
//    @Override
//    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        SimpleEntity simpleEntity = dtomapper.map(new IncomingDto());
//        SimpleEntity saved = service.save(simpleEntity);
//        OutGoingDto map = dtomapper.map(saved);
//        // return our DTO, not necessary
//    }
}
