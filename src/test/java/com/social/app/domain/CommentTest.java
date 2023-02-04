package com.social.app.domain;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class CommentTest {
    private Comment comment;
    private Integer id = 1;
    private String content = "Test comment";
    private String author = "Author";
    private Integer course = 2;
    private Integer likes = 10;
    private Integer[] likedBy = {1, 2, 3};

    @Before
    public void setup() {
        comment = new Comment(id, content, author, course, likes, likedBy);
    }

    @Test
    public void getId_WhenCalled_ShouldReturnId() {
        Assert.assertEquals(id, comment.getId());
    }

    @Test
    public void setId_WhenCalled_ShouldSetId() {
        Integer newId = 2;
        comment.setId(newId);
        Assert.assertEquals(newId, comment.getId());
    }

    @Test
    public void getContent_WhenCalled_ShouldReturnContent() {
        Assert.assertEquals(content, comment.getContent());
    }

    @Test
    public void setContent_WhenCalled_ShouldSetContent() {
        String newContent = "Test comment 2";
        comment.setContent(newContent);
        Assert.assertEquals(newContent, comment.getContent());
    }

    @Test
    public void getAuthor_WhenCalled_ShouldReturnAuthor() {
        Assert.assertEquals(author, comment.getAuthor());
    }

    @Test
    public void setAuthor_WhenCalled_ShouldSetAuthor() {
        String newAuthor = "Author 2";
        comment.setAuthor(newAuthor);
        Assert.assertEquals(newAuthor, comment.getAuthor());
    }

    @Test
    public void getCourse_WhenCalled_ShouldReturnCourse() {
        Assert.assertEquals(course, comment.getCourse());
    }

    @Test
    public void setCourse_WhenCalled_ShouldSetCourse() {
        Integer newCourse = 3;
        comment.setCourse(newCourse);
        Assert.assertEquals(newCourse, comment.getCourse());
    }

    @Test
    public void getLikes_WhenCalled_ShouldReturnLikes() {
        Assert.assertEquals(likes, comment.getLikes());
    }

    @Test
    public void setLikes_WhenCalled_ShouldSetLikes() {
        Integer newLikes = 20;
        comment.setLikes(newLikes);
        Assert.assertEquals(newLikes, comment.getLikes());
    }

    @Test
    public void getLikedBy_WhenCalled_ShouldReturnLikedBy() {
        Assert.assertArrayEquals(likedBy, comment.getLikedBy());
    }

    @Test
    public void setLikedBy_WhenCalled_ShouldSetLikedBy() {
        Integer[] newLikedBy = {4, 5, 6};
        comment.setLikedBy(newLikedBy);
        Assert.assertArrayEquals(newLikedBy, comment.getLikedBy());
    }

}