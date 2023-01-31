package com.social.app.repository;

import com.social.app.domain.Comment;
import com.social.app.exception.UaBadRequestException;
import com.social.app.exception.UaResourceNotFoundException;

import java.util.List;

public interface CommentRepository {
    List<Comment> findCommentsByCourseId(Integer courseId) throws UaResourceNotFoundException;

    Integer create(String content, String author, String course, Integer likes) throws UaBadRequestException;

    void likeComment(Integer userId, Integer commentId) throws UaBadRequestException;

}
