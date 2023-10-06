package org.example.servlet;

import com.google.gson.Gson;
import org.example.service.PostService;
import org.example.service.impl.PostServiceImpl;
import org.example.servlet.dto.PostDto;
import org.example.servlet.dto.TagDto;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/posts")
public class PostServlet extends HttpServlet {
    private final PostService postService = new PostServiceImpl();
    private final Gson gson = new Gson();

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        PostDto postDto = gson.fromJson(req.getReader(), PostDto.class);

        if (postDto.getId() > 0) {
            getPostById(req, resp);
            getAllTagsByPostId(req, resp);
        } else {
            getAllPosts(resp);
        }
    }

    private void getAllPosts(HttpServletResponse resp) throws IOException {
        writeResponse(resp, gson.toJson(postService.findAll()));
    }

    private void getAllTagsByPostId(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        PostDto postDto = gson.fromJson(req.getReader(), PostDto.class);
        List<TagDto> tagsDto = postService.findTagsByPostId(postDto);
        writeResponse(resp, gson.toJson(tagsDto));
    }

    private void getPostById(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        PostDto postDto = gson.fromJson(req.getReader(), PostDto.class);
        writeResponse(resp, gson.toJson(postDto));
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        PostDto postDto = gson.fromJson(req.getReader(), PostDto.class);
        postService.save(postDto);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        PostDto postDto = gson.fromJson(req.getReader(), PostDto.class);
        postService.update(postDto);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        PostDto postDto = gson.fromJson(req.getReader(), PostDto.class);
        postService.deleteById(postDto);
    }

    private void writeResponse(HttpServletResponse resp, String jsonString) throws IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        PrintWriter out = resp.getWriter();
        out.print(jsonString);
        out.flush();
    }
}
