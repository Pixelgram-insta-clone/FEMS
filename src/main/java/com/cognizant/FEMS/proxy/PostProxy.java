package com.cognizant.FEMS.proxy;
import com.cognizant.FEMS.model.PageOfItems;
import com.cognizant.FEMS.model.Post;
import com.cognizant.FEMS.model.PostDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(value = "post-proxy", url = "${tmem-post}")
public interface PostProxy {

    @GetMapping("/posts")
    PageOfItems<Post> getPosts(
            @RequestParam int pageNumber,
            @RequestParam int pageSize
    );

    @PostMapping("/users/{userId}/posts")
    Post createNewPost (
            @PathVariable int userId,
            @RequestBody PostDTO postDTO
    );

    @DeleteMapping("/posts")
    Boolean deletePost(
            @RequestParam int postId
    );
}
