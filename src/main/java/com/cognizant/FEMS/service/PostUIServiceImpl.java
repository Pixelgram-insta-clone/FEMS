package com.cognizant.FEMS.service;

import com.cognizant.FEMS.model.PageOfItems;
import com.cognizant.FEMS.model.Post;
import com.cognizant.FEMS.model.PostUI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class PostUIServiceImpl implements PostUIService {

    @Autowired
    private PostServiceImpl postService;

    @Autowired
    private CommentServiceImpl commentService;

    @Autowired
    private UserServiceImpl userService;

    public PageOfItems<PostUI> getPublicPosts(int pageNumber, int pageSize) {

        if (pageNumber < 0 || pageSize <= 0) return new PageOfItems<>();

        PageOfItems<Post> posts = postService.getPublicPosts(pageNumber, pageSize);

        List<PostUI> postList = new ArrayList<>();

        posts.getItems().forEach(post -> {
            postList.add(new PostUI(
                    post.getId(),
                    userService.getUser(post.getUserId()),
                    post.getImg(),
                    post.getDescription(),
                    post.getCreatedOn(),
                    commentService.getPublicComments(post.getId(), 0, 5)
            ));
        });

        return new PageOfItems<>(
                postList,
                posts.isHasNext(),
                posts.getTotalElements()
        );

    }
}
