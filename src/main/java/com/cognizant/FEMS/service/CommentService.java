package com.cognizant.FEMS.service;

import com.cognizant.FEMS.model.Comment;
import com.cognizant.FEMS.model.CommentDTO;
import com.cognizant.FEMS.model.PageOfItems;

public interface CommentService {
    PageOfItems<Comment> getPublicComments(int postId, int pageNumber, int pageSize);
    public Comment createNewComment(int postId, CommentDTO commentDTO);

}
