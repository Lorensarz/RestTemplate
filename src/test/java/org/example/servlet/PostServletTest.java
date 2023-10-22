package org.example.servlet;

import com.google.gson.Gson;
import org.example.service.PostService;
import org.example.servlet.dto.PostDto;
import org.example.servlet.dto.TagDto;
import org.example.servlet.mapper.PostDtoMapper;
import org.example.servlet.mapper.PostDtoMapperImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;


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
        PostDto postDto = new PostDto();
        postDto.setId(1L);
        postDto.setTitle("Post 1");
        postDto.setContent("Content for post 1");
        postDto.setUserId(1L);
        List<TagDto> tags = Arrays.asList(new TagDto(1L, "Tag 1"), new TagDto(2L, "Tag 2"));
        postDto.setTags(tags);

        PostDto postDto2 = new PostDto();
        postDto2.setId(2L);
        postDto2.setTitle("Post 2");
        postDto2.setContent("Content for post 2");
        postDto2.setUserId(2L);
        List<TagDto> tags2 = List.of(new TagDto(2L, "Tag 2"));
        postDto2.setTags(tags2);

        List<PostDto> expectedPosts = new ArrayList<>();
        expectedPosts.add(postDto);
        expectedPosts.add(postDto2);

        when(request.getParameter("user_id")).thenReturn("1","2");
        when(postService.findPostsByUserId(anyLong())).thenReturn(postDtoMapper.toEntityList(expectedPosts));

        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);

        postServlet.doGet(request, response);

        verify(response).setContentType("application/json");
        verify(response).setCharacterEncoding("UTF-8");

        writer.flush();
        assertEquals(gson.toJson(expectedPosts), stringWriter.toString());
    }

    @Test
    public void testDoGetAllPosts() throws IOException {
        List<PostDto> posts = new ArrayList<>();
        posts.add(new PostDto(1L, "Post 1", "Content for post 1",
                1L, Arrays.asList(new TagDto(1L, "Tag 1"), new TagDto(2L, "Tag 2"))));
        posts.add(new PostDto(2L, "Post 2", "Content for post 2",
                2L, List.of(new TagDto(2L, "Tag 2"))));
        when(request.getParameter("id")).thenReturn(null);
        when(postService.findAll()).thenReturn(postDtoMapper.toEntityList(posts));

        StringWriter actualStringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(actualStringWriter);
        when(response.getWriter()).thenReturn(writer);

        postServlet.doGet(request, response);

        verify(response).setContentType("application/json");
        verify(response).setCharacterEncoding("UTF-8");

        String expectedJson = gson.toJson(posts);
        writer.flush();
        Assertions.assertEquals(expectedJson, actualStringWriter.toString());
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
