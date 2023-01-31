package com.social.app.resources;

import com.social.app.domain.Comment;
import com.social.app.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/comments")
public class CommentResource {

    @Autowired
    CommentService commentService;

    @GetMapping("/{courseId}")
    public ResponseEntity<List<Comment>> getCommentsByCourse(@PathVariable("courseId") Integer courseId) {
        List<Comment> comments = commentService.findCommentsByCourseId(courseId);
        return new ResponseEntity<>(comments, HttpStatus.OK);
    }

    @PostMapping("/add-comment")
    public ResponseEntity<Integer> createComment(@RequestBody Map<String, Object> commentMap) {
        String content = (String) commentMap.get("content");
        String author = (String) commentMap.get("author");
        String course = (String) commentMap.get("course");
        Integer likes = (Integer) commentMap.get("likes");

        Integer comment = commentService.create(content, author,course, likes);

        return new ResponseEntity<>(comment, HttpStatus.OK);
    }

    @PutMapping("/like/{userId}/{courseId}")
    public ResponseEntity<Map<String, Boolean>> likeComment(@PathVariable("userId") Integer userId,
                                                            @PathVariable("courseId") Integer courseId) {
        commentService.likeComment(userId, courseId);
        Map<String, Boolean> map = new HashMap<>();
        map.put("success", true);
        return new ResponseEntity<>(map, HttpStatus.OK);
    }
}
