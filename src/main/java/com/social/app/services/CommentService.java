package com.social.app.services;

import com.social.app.domain.Comment;
import com.social.app.exception.UaBadRequestException;
import com.social.app.exception.UaResourceNotFoundException;

import java.util.List;

public interface CommentService {

    List<Comment> findCommentsByCourseId(Integer courseId) throws UaResourceNotFoundException;

    Integer create(String content, String author, Integer course, Integer likes) throws UaBadRequestException;

    void likeComment(Integer userId, Integer commentId) throws UaBadRequestException;

}
