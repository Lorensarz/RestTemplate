package org.example.servlet;

import com.google.gson.Gson;
import org.example.model.PostEntity;
import org.example.service.PostService;
import org.example.servlet.dto.PostDto;
import org.example.servlet.dto.TagDto;
import org.example.servlet.mapper.PostDtoMapper;
import org.example.servlet.mapper.PostDtoMapperImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class PostServletTest {

    @Mock
    private PostService postService;
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private RequestDispatcher dispatcher;
    @InjectMocks
    private PostServlet postServlet;
    private final PostDtoMapper postDtoMapper = new PostDtoMapperImpl();
    private final Gson gson = new Gson();

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        when(request.getRequestDispatcher(anyString())).thenReturn(dispatcher);
    }

    @Test
    public void testDoGet() throws IOException {
        PostDto postDto = new PostDto(1L, "Test Post", "This is a test post",
                1L, Collections.singletonList(new TagDto(1L, "Test Tag")));
        PostEntity postEntity = postDtoMapper.toEntity(postDto);

        when(request.getParameter("id")).thenReturn("1");
        when(postService.findById(anyLong())).thenReturn(postEntity);

        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);

        postServlet.doGet(request, response);

        verify(response).setContentType("application/json");
        verify(response).setCharacterEncoding("UTF-8");

        String expectedJson = gson.toJson(postDto);
        writer.flush();
        assertEquals(expectedJson, stringWriter.toString());
    }

    @Test
    public void testDoGetAllPosts() throws IOException {
        List<PostDto> posts = new ArrayList<>();
        posts.add(new PostDto(1L, "Test Post", "This is a test post",
                1L, Collections.singletonList(new TagDto(1L, "Test Tag"))));
        posts.add(new PostDto(2L, "Test Post2", "This is a test post2",
                2L, Collections.singletonList(new TagDto(2L, "Test Tag2"))));
        when(request.getParameter("id")).thenReturn(null);
        when(postService.findAll()).thenReturn(postDtoMapper.toEntityList(posts));

        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);

        postServlet.doGet(request, response);

        verify(response).setContentType("application/json");
        verify(response).setCharacterEncoding("UTF-8");

        String expectedJson = gson.toJson(posts);
        writer.flush();
        assertEquals(expectedJson, stringWriter.toString());
    }

    @Test
    public void testDoPost() throws IOException {
        String json = "{\"id\": 1, \"title\": \"New Post\", \"content\": \"This is a new post\", \"userId\": 1, \"tags\": []}";
        BufferedReader reader = new BufferedReader(new StringReader(json));
        when(request.getReader()).thenReturn(reader);

        postServlet.doPost(request, response);

        verify(postService).save(any(PostDto.class));
    }

    @Test
    public void testDoPut() throws IOException {
        String json = "{\"id\": 1, \"title\": \"Updated Post\", \"content\": \"This is an updated post\", \"userId\": 1, \"tags\": []}";
        BufferedReader reader = new BufferedReader(new StringReader(json));
        when(request.getReader()).thenReturn(reader);

        postServlet.doPut(request, response);

        verify(postService).update(any(PostDto.class));
    }

    @Test
    public void testDoDelete() throws IOException {
        String id = "1";
        when(request.getParameter("id")).thenReturn(id);
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);
        postServlet.doDelete(request, response);
        verify(postService).deleteById(Long.parseLong(id));
    }
}
