package com.cognizant.FEMS.controller;

import com.cognizant.FEMS.model.*;
import com.cognizant.FEMS.service.CommentServiceImpl;
import com.cognizant.FEMS.service.PostServiceImpl;
import com.cognizant.FEMS.service.PostUIServiceImpl;
import com.cognizant.FEMS.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;


@RestController
@CrossOrigin(origins = "${tmem-ui}", allowCredentials = "true")
public class FEMsController {

    @Autowired
    private PostUIServiceImpl postUIService;

    @Autowired
    private PostServiceImpl postService;

    @Autowired
    private CommentServiceImpl commentService;

    @Autowired
    private UserServiceImpl userService;

    @GetMapping("/posts")
    public PageOfItems<PostUI> getPaginatedPosts(
            @RequestParam int pageNumber,
            @RequestParam int pageSize
    ) {
        return postUIService.getPublicPosts(pageNumber, pageSize);
    }

    @GetMapping("/posts/{postId}/comments")
    public PageOfItems<Comment> getCommentsByPost(
            @PathVariable int postId,
            @RequestParam int pageNumber,
            @RequestParam int pageSize
    ) {
        return commentService.getPublicComments(postId, pageNumber, pageSize);
    }

    @PostMapping("/users/{userId}/posts")
    public Post createNewPost(
            @PathVariable int userId,
            @RequestBody PostDTO postDTO
    ) {
        return postService.createNewPost(userId, postDTO);
    }

    @PostMapping("/posts/{postId}/comments")
    public Comment createNewComment(
            @PathVariable int postId,
            @RequestBody CommentDTO commentDTO
    ) {
        return commentService.createNewComment(postId, commentDTO);
    }

    @GetMapping("/users/{userId}")
    public User getUser(
            @PathVariable int userId

    ) {
        return userService.getUser(userId);
    }

}
