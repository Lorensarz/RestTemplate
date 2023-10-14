package org.example.servlet;

import com.google.gson.Gson;
import org.example.db.ConnectionManager;
import org.example.db.MySQLConnection;
import org.example.repository.impl.TagRepositoryImpl;
import org.example.service.TagService;
import org.example.service.impl.TagServiceImpl;
import org.example.servlet.dto.PostDto;
import org.example.servlet.dto.TagDto;
import org.example.servlet.mapper.PostDtoMapperImpl;
import org.example.servlet.mapper.TagDtoMapperImpl;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/tags")
public class TagServlet extends HttpServlet {
    private final ConnectionManager connectionManager = new MySQLConnection();
    private final TagService tagService = new TagServiceImpl(
            new TagRepositoryImpl(connectionManager),
            new TagDtoMapperImpl(),
            new PostDtoMapperImpl());

    private final Gson gson = new Gson();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        PostDto postDto = gson.fromJson(req.getReader(), PostDto.class);
        List<TagDto> posts = tagService.findTagsByPost(postDto);
        writeResponse(resp, gson.toJson(posts));
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        PostDto postDto = gson.fromJson(req.getReader(), PostDto.class);
        TagDto tagDto = gson.fromJson(req.getReader(), TagDto.class);
        tagService.addTagToPost(postDto, tagDto);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        PostDto postDto = gson.fromJson(req.getReader(), PostDto.class);
        TagDto tagDto = gson.fromJson(req.getReader(), TagDto.class);
        tagService.removeTagFromPost(postDto, tagDto);
    }

    private void writeResponse(HttpServletResponse resp, String jsonString) throws IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        PrintWriter out = resp.getWriter();
        out.print(jsonString);
        out.flush();
    }
}
