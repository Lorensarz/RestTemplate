package org.example.servlet;

import com.google.gson.Gson;
import org.example.db.ConnectionManager;
import org.example.db.MySQLConnection;
import org.example.repository.PostRepository;
import org.example.repository.impl.PostRepositoryImpl;
import org.example.service.PostService;
import org.example.service.impl.PostServiceImpl;
import org.example.servlet.dto.PostDto;
import org.example.servlet.mapper.PostDtoMapper;
import org.example.servlet.mapper.PostDtoMapperImpl;
import org.example.servlet.mapper.TagDtoMapper;
import org.example.servlet.mapper.TagDtoMapperImpl;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/posts")
public class PostServlet extends HttpServlet {

    private final PostRepository postRepository;
    private final PostDtoMapper postDtoMapper;
    private final TagDtoMapper tagDtoMapper;
    private ConnectionManager connectionManager = new MySQLConnection();

    private PostService postService = new PostServiceImpl(
            postRepository = new PostRepositoryImpl(connectionManager),
            postDtoMapper = new PostDtoMapperImpl(),
            tagDtoMapper = new TagDtoMapperImpl());
    private final Gson gson = new Gson();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String userId = req.getParameter("user_id");
        if (userId != null) {
            List<PostDto> postsDto = postDtoMapper.toDtoList(postService.findPostsByUserId(Long.parseLong(userId)));

            writeResponse(resp, gson.toJson(postsDto));
        } else {
            getAllPosts(resp);
        }

    }

    private void getAllPosts(HttpServletResponse resp) throws IOException {
        writeResponse(resp, gson.toJson(postService.findAll()));
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
        String id = req.getParameter("id");
        if (id != null && !id.isEmpty()) {
            postService.deleteById(Long.parseLong(id));
            writeResponse(resp, "Post deleted successfully");
        } else {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            writeResponse(resp, "Invalid request: 'id' parameter is missing");
        }
    }

    private void writeResponse(HttpServletResponse resp, String string) throws IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        PrintWriter out = resp.getWriter();
        out.print(string);
        out.flush();
    }
}
