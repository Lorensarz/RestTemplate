package org.example.servlet;

import com.google.gson.Gson;
import org.example.model.PostEntity;
import org.example.model.TagEntity;
import org.example.service.PostService;
import org.example.service.impl.PostServiceImpl;
import org.example.servlet.dto.PostDto;
import org.example.servlet.dto.TagDto;
import org.example.servlet.mapper.PostDtoMapper;
import org.example.servlet.mapper.PostDtoMapperImpl;
import org.example.servlet.mapper.TagDtoMapper;
import org.example.servlet.mapper.TagDtoMapperImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(name = "postServlet", urlPatterns = {"/posts"})
public class PostServlet extends HttpServlet {
    private final PostService postService = new PostServiceImpl();
    private final PostDtoMapper postMapper = new PostDtoMapperImpl();
    private final TagDtoMapper tagMapper = new TagDtoMapperImpl();
    private final Gson gson = new Gson();

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String postIdStr = req.getParameter("postId");
        String id = req.getParameter("id");

        if (postIdStr != null) {
            long parsPostId = Long.parseLong(postIdStr);
            getAllTagsByPostId(resp, parsPostId);
        } else if (id != null) {
            long parsId = Long.parseLong(id);
            getPostById(resp, parsId);
        } else {
            getAllPosts(resp);
        }
    }

    private void getAllPosts(HttpServletResponse resp) throws IOException {
        List<PostEntity> posts = postService.findAll();
        List<PostDto> postsDto = postMapper.toDtoList(posts);
        writeResponse(resp, gson.toJson(postsDto));
    }

    private void getAllTagsByPostId(HttpServletResponse resp, long id) throws IOException {
        List<TagEntity> tags = postService.findTagsByPostId(id);
        List<TagDto> tagsDto = tagMapper.toDtoList(tags);
        writeResponse(resp, gson.toJson(tagsDto));
    }

    private void getPostById(HttpServletResponse resp, long id) throws IOException {
        PostEntity postEntity = postService.findById(id);
        PostDto postDto = postMapper.toDto(postEntity);
        writeResponse(resp, gson.toJson(postDto));
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PostDto postDto = gson.fromJson(req.getReader(), PostDto.class);
        PostEntity post = postMapper.toEntity(postDto);
        postService.save(post);
        writeResponse(resp, gson.toJson(post));
        resp.setStatus(200);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PostDto postDto = gson.fromJson(req.getReader(), PostDto.class);
        PostEntity post = postMapper.toEntity(postDto);
        postService.update(post);
        writeResponse(resp, gson.toJson(post));
        resp.setStatus(200);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        postService.deleteById(Long.parseLong(id));
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
