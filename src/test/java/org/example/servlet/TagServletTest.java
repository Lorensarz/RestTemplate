package org.example.servlet;

import com.google.gson.Gson;
import org.example.service.TagService;
import org.example.servlet.dto.PostDto;
import org.example.servlet.dto.TagDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class TagServletTest {

    @Mock
    private TagService tagService;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @InjectMocks
    private TagServlet tagServlet;

    private final Gson gson = new Gson();

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testDoGet() throws Exception {
        PostDto postDto = new PostDto();
        postDto.setId(1L);
        postDto.setTitle("Post 1");
        postDto.setContent("Content for post 1");
        postDto.setUserId(1L);

        List<TagDto> tags = Arrays.asList(new TagDto(1L, "Tag 1"), new TagDto(2L, "Tag 2"));
        postDto.setTags(tags);


        String jsonInput = gson.toJson(postDto);
        when(request.getReader()).thenReturn(new BufferedReader(new StringReader(jsonInput)));

        when(tagService.findTagsByPost(any(PostDto.class))).thenReturn(tags);

        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);

        tagServlet.doGet(request, response);

        verify(response).setContentType("application/json");
        verify(response).setCharacterEncoding("UTF-8");

        writer.flush();
        assertEquals(gson.toJson(tags), stringWriter.toString());
    }

    @Test
    public void testDoPost() throws Exception {
        PostDto postDto = new PostDto();
        postDto.setId(1L);
        postDto.setTitle("Post 1");
        postDto.setContent("Content for post 1");
        postDto.setUserId(1L);

        String jsonInput = gson.toJson(postDto);
        when(request.getReader()).thenReturn(new BufferedReader(new StringReader(jsonInput)));

        tagServlet.doPost(request, response);

        verify(tagService).addTagToPost(any(PostDto.class));
    }

    @Test
    public void testDoPut() throws Exception {
        PostDto postDto = new PostDto();
        postDto.setId(1L);
        postDto.setTitle("Post 1");
        postDto.setContent("Updated Content");
        postDto.setUserId(1L);

        String jsonInput = gson.toJson(postDto);
        when(request.getReader()).thenReturn(new BufferedReader(new StringReader(jsonInput)));

        tagServlet.doPut(request, response);

        verify(tagService).updateTagForPost(any(PostDto.class));
    }

    @Test
    public void testDoDelete() throws Exception {
        PostDto postDto = new PostDto();
        postDto.setId(1L);
        postDto.setTitle("Post 1");
        postDto.setContent("Content for post 1");
        postDto.setUserId(1L);

        TagDto tagDto = new TagDto();
        tagDto.setId(1L);
        tagDto.setName("Tag 1");
        postDto.setTags(List.of(tagDto));

        String postJson = gson.toJson(postDto);
        when(request.getReader()).thenReturn(new BufferedReader(new StringReader(postJson)));

        tagServlet.doDelete(request, response);

        verify(tagService).removeTagFromPost(any(PostDto.class));

    }
}
