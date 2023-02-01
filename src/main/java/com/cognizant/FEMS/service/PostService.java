package com.cognizant.FEMS.service;

import com.cognizant.FEMS.model.PageOfItems;
import com.cognizant.FEMS.model.Post;
import com.cognizant.FEMS.model.PostDTO;

public interface PostService {
    public PageOfItems<Post> getPublicPosts(int pageNumber, int pageSize);
    public Post createNewPost(int userId, PostDTO postDTO);
}

