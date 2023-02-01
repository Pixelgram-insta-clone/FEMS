package com.cognizant.FEMS;

import com.cognizant.FEMS.model.*;
import com.cognizant.FEMS.service.CommentServiceImpl;
import com.cognizant.FEMS.service.PostServiceImpl;
import com.cognizant.FEMS.service.PostUIServiceImpl;
import com.cognizant.FEMS.service.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PostUIServiceTest {

    @Mock
    private PostServiceImpl postService;

    @Mock
    private CommentServiceImpl commentService;

    @Mock
    private UserServiceImpl userService;

    @InjectMocks
    private PostUIServiceImpl postUIService;

    private static Comment comment;
    private static Post post;
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

        comment = new Comment(
                1,
                1,
                "Paul",
                "The Bene Gesserit is an ancient order",
                LocalDate.now()
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
    }

    @Test
    public void getPublicPosts_positiveTest() {
        when(postService.getPublicPosts(1, 1))
                .thenReturn(pageOfPosts);
        when(commentService.getPublicComments(1, 0, 5))
                .thenReturn(pageOfComments);
        when(userService.getUser(1)).thenReturn(user);


        List<PostUI> postUIList = new ArrayList<>();

        pageOfPosts.getItems().forEach(post -> {
            postUIList.add(new PostUI(
                    post.getId(),
                    user,
                    post.getImg(),
                    post.getDescription(),
                    post.getCreatedOn(),
                    commentService.getPublicComments(1, 0, 5)
            ));
        });

        PageOfItems<PostUI> expected = new PageOfItems<>(
                postUIList,
                pageOfPosts.isHasNext(),
                pageOfPosts.getTotalElements()

        );
        PageOfItems<PostUI> actual = postUIService.getPublicPosts(1, 1);

        Assertions.assertThat(actual).isEqualTo(expected);

    }

    @Test
    public void getPublicPostsZeroPost_positiveTest() {
        PageOfItems<PostUI> actual = postUIService.getPublicPosts(0, 0);

        Assertions.assertThat( actual.getTotalElements() == 0 );
    }
}
