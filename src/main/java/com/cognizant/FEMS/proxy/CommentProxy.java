package com.cognizant.FEMS.proxy;
import com.cognizant.FEMS.model.Comment;
import com.cognizant.FEMS.model.CommentDTO;
import com.cognizant.FEMS.model.PageOfItems;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;


@FeignClient(value="comment-proxy", url = "${tmem-comment}")
public interface CommentProxy {

    @GetMapping("/comments")
    PageOfItems<Comment> getComments(
            @RequestParam int postId,
            @RequestParam int pageNumber,
            @RequestParam int pageSize
    );

    @PostMapping("/posts/{postId}/comments")
    Comment createNewComment(
            @PathVariable int postId,
            @RequestBody CommentDTO commentDTO
    );

    @DeleteMapping("/comments")
    Boolean deleteComment(
            @RequestParam int commentId
    );
}
