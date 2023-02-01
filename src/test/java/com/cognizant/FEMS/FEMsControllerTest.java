package com.cognizant.FEMS;

import com.cognizant.FEMS.controller.FEMsController;
import com.cognizant.FEMS.model.*;
import com.cognizant.FEMS.service.CommentServiceImpl;
import com.cognizant.FEMS.service.PostServiceImpl;
import com.cognizant.FEMS.service.PostUIServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FEMsControllerTest {

    @Mock
    private PostUIServiceImpl postUIService;

    @Mock
    private PostServiceImpl postService;

    @Mock
    private CommentServiceImpl commentService;

    @InjectMocks
    private FEMsController controller;

    private static Comment comment;
    private static CommentDTO commentDTO;
    private static Post post;
    private static Post postTwo;
    private static PostDTO postDTO;
    private static PostDTO postTwoDTO;
    private static PageOfItems<Post> pageOfPosts;
    private static PageOfItems<Comment> pageOfComments;
    private static PostUI postUI;
    private static PageOfItems<PostUI> pageOfPostUIs;
    private static User user;

    @BeforeAll
    public static void init() {
        post = new Post(
                1,
                1,
                "imageUrl",
                "Muaddib the conquerer",
                LocalDate.now()
        );

        postDTO = new PostDTO(
                post.getImg(),
                post.getDescription()
        );

        postTwo = new Post(
                2,
                2,
                "imageUrl",
                "Muaddib the conquerer!",
                LocalDate.now()
        );

        postTwoDTO = new PostDTO(
                postTwo.getImg(),
                postTwo.getDescription()
        );

        comment = new Comment(
                1,
                1,
                "Paul",
                "The Bene Gesserit is an ancient order of control oooo",
                LocalDate.now()
        );

        commentDTO = new CommentDTO(
                comment.getUsername(),
                comment.getBody()
        );

        user = new User(
                1,
                "username",
                "profile_img.jpg"
        );

        List<Post> posts = new ArrayList<>();
        posts.add(post);
        pageOfPosts = new PageOfItems<>(new PageImpl<>(posts));

        List<Comment> comments = new ArrayList<>();
        comments.add(comment);
        pageOfComments = new PageOfItems<>(new PageImpl<>(comments));

        List<PostUI> postList = new ArrayList<>();

        pageOfPosts.getItems().forEach(post -> {
            postList.add(new PostUI(
                    post.getId(),
                    user,
                    post.getImg(),
                    post.getDescription(),
                    post.getCreatedOn(),
                    pageOfComments
            ));
        });

        pageOfPostUIs = new PageOfItems<>(
                postList,
                pageOfPosts.isHasNext(),
                pageOfPosts.getTotalElements()
        );

    }

    @Test
    public void getPaginatedPosts_positiveTest() {
        when(postUIService.getPublicPosts(0, 1))
                .thenReturn(pageOfPostUIs);

        PageOfItems<PostUI> actual = postUIService.getPublicPosts(0, 1);

        Assertions.assertThat(actual).isEqualTo(pageOfPostUIs);

    }

    @Test
    public void createNewPost_positiveTest() {
        when(postService.createNewPost(
                post.getUserId(),
                postDTO)
        ).thenReturn(post);
        Post actual = controller.createNewPost(
                post.getUserId(),
                postDTO
        );
        Assertions.assertThat(actual).isEqualTo(post);
    }

    @Test
    public void createAnotherPost_positiveTest() {
        when(postService.createNewPost(
                postTwo.getUserId(),
                postTwoDTO)
        ).thenReturn(postTwo);
        Post actual = controller.createNewPost(
                postTwo.getUserId(),
                postTwoDTO
        );
        Assertions.assertThat(actual).isEqualTo(postTwo);
    }

    @Test
    public void createNewComment_positiveTest() {
        when(commentService.createNewComment(
                comment.getPostId(),
                commentDTO)
        ).thenReturn(comment);
        Comment actual = controller.createNewComment(
                comment.getPostId(),
                commentDTO
        );
        Assertions.assertThat(actual).isEqualTo(comment);

    }

}
