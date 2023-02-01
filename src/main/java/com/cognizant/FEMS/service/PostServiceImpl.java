package com.cognizant.FEMS.service;
import com.cognizant.FEMS.model.PageOfItems;
import com.cognizant.FEMS.model.Post;
import com.cognizant.FEMS.model.PostDTO;
import com.cognizant.FEMS.proxy.PostProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostProxy postProxy;

    public PageOfItems<Post> getPublicPosts(int pageNumber, int pageSize) {

        if (pageNumber < 0 || pageSize < 0) return new PageOfItems<Post>();

        PageOfItems<Post> posts = postProxy.getPosts(pageNumber, pageSize);

        return (posts != null) ? posts : new PageOfItems<>();
    }

    public Post createNewPost(int userId, PostDTO postDTO) {
        return postProxy.createNewPost(userId, postDTO);
    }

}
