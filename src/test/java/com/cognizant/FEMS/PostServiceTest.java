package com.cognizant.FEMS;

import com.cognizant.FEMS.model.PageOfItems;
import com.cognizant.FEMS.model.Post;
import com.cognizant.FEMS.model.PostDTO;
import com.cognizant.FEMS.proxy.PostProxy;
import com.cognizant.FEMS.service.PostServiceImpl;
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
public class PostServiceTest {

    @Mock
    private PostProxy postProxy;

    @InjectMocks
    private PostServiceImpl postService;

    private static Post post;
    private static PostDTO postDTO;
    private static PageOfItems<Post> pageOfPosts;

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

        List<Post> posts = new ArrayList<>();
        posts.add(post);
        pageOfPosts = new PageOfItems<>(new PageImpl<>(posts));
    }

    @Test
    public void getPublicPosts_positiveTest() {
        when(postProxy.getPosts(1,1))
                .thenReturn(pageOfPosts);

        PageOfItems<Post> actual = postService.getPublicPosts(1, 1);

        Assertions.assertThat(actual).isEqualTo(pageOfPosts);
    }

    @Test
    public void getPublicPostNullPosts_positiveTest() {
        when(postProxy.getPosts(1, 1))
                .thenReturn(null);

        PageOfItems<Post> actual = postService.getPublicPosts(1, 1);

        Assertions.assertThat(actual.getTotalElements() == 0);
    }

    @Test
    public void getPublicPostZeroPosts_positiveTest() {
        PageOfItems<Post> actual = postService.getPublicPosts(0, 0);

        Assertions.assertThat(actual.getTotalElements() == 0);
    }

    @Test
    public void getNewPost_positiveTest() {
        when(postProxy.createNewPost(post.getUserId(), postDTO)).thenReturn(post);

        Post actual = postService.createNewPost(post.getUserId(), postDTO);

        Assertions.assertThat(actual).isEqualTo(post);
    }
}
