package com.cognizant.FEMS;

import com.cognizant.FEMS.model.Comment;
import com.cognizant.FEMS.model.CommentDTO;
import com.cognizant.FEMS.model.PageOfItems;
import com.cognizant.FEMS.proxy.CommentProxy;
import com.cognizant.FEMS.service.CommentServiceImpl;
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
public class CommentServiceTest {

    @Mock
    private CommentProxy commentProxy;

    @InjectMocks
    private CommentServiceImpl commentService;

    private static Comment comment;
    private static CommentDTO commentDTO;

    @BeforeAll
    public static void init() {
        comment = new Comment(
                1,
                1,
                "Paul",
                "The Bene Gesserit is an ancient order",
                LocalDate.now()
        );

        commentDTO = new CommentDTO(
                comment.getUsername(),
                comment.getBody()
        );
    }

    @Test
    public void getPublicComments_positiveTest() {
        List<Comment> comments = new ArrayList<>();
        comments.add(comment);
        PageOfItems<Comment> expected = new PageOfItems<>(new PageImpl<Comment>(comments));
        when(commentProxy.getComments(1, 1, 1))
                .thenReturn(expected);

        PageOfItems<Comment> actual = commentService.getPublicComments(1, 1, 1);

        Assertions.assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void getPublicCommentsNoComments_positiveTest() {
        PageOfItems<Comment> actual = commentService.getPublicComments(1, 0, 0);

        Assertions.assertThat( actual.getTotalElements() == 0 );
    }

    @Test
    public void getNewComment_positiveTest() {
        when(commentProxy.createNewComment(comment.getPostId(), commentDTO)).thenReturn(comment);
        Comment actual = commentService.createNewComment(comment.getPostId(), commentDTO);
        Assertions.assertThat(actual).isEqualTo(comment);
    }
}
