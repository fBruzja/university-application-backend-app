package com.social.app.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import com.social.app.domain.Comment;
import com.social.app.exception.UaBadRequestException;
import com.social.app.exception.UaResourceNotFoundException;
import com.social.app.repository.CommentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class CommentServiceImplTest {

    @Mock
    CommentRepository commentRepository;

    @InjectMocks
    CommentServiceImpl commentService;

    private List<Comment> comments;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        Integer array[] = new Integer[]{};
        comments = new ArrayList<>();
        comments.add(new Comment(1, "Hello", "John", 1, 10, array));
        comments.add(new Comment(2, "Hi", "Jane", 1, 5, array));
    }

    @Test
    void testFindCommentsByCourseId() throws UaResourceNotFoundException {
        when(commentRepository.findCommentsByCourseId(1)).thenReturn(comments);
        List<Comment> returnedComments = commentService.findCommentsByCourseId(1);
        assertThat(returnedComments).isEqualTo(comments);
    }

    @Test
    void testCreate() throws UaBadRequestException {
        when(commentRepository.create("Hello", "John", "1", 10)).thenReturn(1);
        Integer commentId = commentService.create("Hello", "John", "1", 10);
        assertThat(commentId).isEqualTo(1);
    }

    @Test
    void testLikeComment() throws UaBadRequestException {
        commentService.likeComment(1, 1);
    }
}
