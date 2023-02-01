package com.cognizant.FEMS.service;

import com.cognizant.FEMS.model.Comment;
import com.cognizant.FEMS.model.CommentDTO;
import com.cognizant.FEMS.model.PageOfItems;
import com.cognizant.FEMS.proxy.CommentProxy;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.RequiredTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService{

    @Autowired
    private CommentProxy commentProxy;

    public PageOfItems<Comment> getPublicComments(int postId, int pageNumber, int pageSize) {

        if ( pageNumber < 0 || pageSize <= 0 ) return new PageOfItems<>();

        PageOfItems<Comment> comments = commentProxy.getComments(postId, pageNumber, pageSize);

        return ( comments != null ) ? comments : new PageOfItems<>();
    }

    @Override
    public Comment createNewComment(int postId, CommentDTO commentDTO) {
        return commentProxy.createNewComment(postId, commentDTO);
    }

}
