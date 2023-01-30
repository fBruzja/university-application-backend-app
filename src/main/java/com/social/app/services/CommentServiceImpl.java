package com.social.app.services;

import com.social.app.domain.Comment;
import com.social.app.exception.UaBadRequestException;
import com.social.app.exception.UaResourceNotFoundException;
import com.social.app.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CommentServiceImpl implements CommentService {

    @Autowired
    CommentRepository commentRepository;

    @Override
    public List<Comment> findCommentsByCourseId(Integer courseId) throws UaResourceNotFoundException {
        return commentRepository.findCommentsByCourseId(courseId);
    }

    @Override
    public Integer create(String content, String author, Integer course, Integer likes) throws UaBadRequestException {
        return commentRepository.create(content, author, course, likes);
    }

    @Override
    public void likeComment(Integer userId, Integer commentId) throws UaBadRequestException {
        commentRepository.likeComment(userId, commentId);
    }
}
