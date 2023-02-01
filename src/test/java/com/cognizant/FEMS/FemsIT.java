package com.cognizant.FEMS;

import com.cognizant.FEMS.model.Comment;
import com.cognizant.FEMS.model.CommentDTO;
import com.cognizant.FEMS.model.Post;
import com.cognizant.FEMS.model.PostDTO;
import com.cognizant.FEMS.proxy.CommentProxy;
import com.cognizant.FEMS.proxy.PostProxy;
import com.cognizant.FEMS.service.PostServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.context.WebApplicationContext;

import javax.print.attribute.standard.Media;
import java.lang.reflect.Field;
import java.time.LocalDate;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class FemsIT {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private PostProxy postProxy;

    @Autowired
    private CommentProxy commentProxy;

    private static Post post;
    private static PostDTO postDTO;
    private static Comment comment;
    private static CommentDTO commentDTO;
    private static LocalDate date;

    @BeforeAll()
    public static void init() {

        date = LocalDate.now();

        post = new Post(
                1,
                1,
                "imageUrl",
                "Muaddib the conquerer",
                date
        );

        postDTO = new PostDTO(
                post.getImg(),
                post.getDescription()
        );

        comment = new Comment(
                1,
                1,
                "Paul",
                "The Bene Gesserit is an ancient order of control oooo",
                date
        );

        commentDTO = new CommentDTO(
                comment.getUsername(),
                comment.getBody()
        );
    }

    @Test
    public void getAllPosts_integrationTest() throws Exception {
        MvcResult result = mvc.perform(MockMvcRequestBuilders.
                get("/posts?pageNumber=0&pageSize=1")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    public void createNewPost_integrationTest() throws Exception {
        String json = mapper.writeValueAsString(postDTO);
        MvcResult result = mvc.perform(MockMvcRequestBuilders
                .post("/users/1/posts")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        Post actual = mapper.readValue(result.getResponse().getContentAsString(), Post.class);
        Assertions.assertTrue(postProxy.deletePost(actual.getId()));

        Post expected = post;
        expected.setId(actual.getId());

        Assertions.assertEquals(expected, actual);

    }

    @Test
    public void createNewComment_integrationTest() throws Exception {
        String json = mapper.writeValueAsString(commentDTO);
        MvcResult result = mvc.perform(MockMvcRequestBuilders
                .post("/posts/1/comments")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        Comment actual = mapper.readValue(result.getResponse().getContentAsString(), Comment.class);
        Assertions.assertTrue(commentProxy.deleteComment(actual.getId()));

        Comment expected = comment;
        expected.setId(actual.getId());

        Assertions.assertEquals(expected, actual);
    }
}
