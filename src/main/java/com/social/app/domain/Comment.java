package com.social.app.domain;

public class Comment {
    private Integer id;
    private String content;
    private String author;
    private Integer course;
    private Integer likes;
    private Integer[] likedBy;

    public Comment(Integer id, String content, String author, Integer course, Integer likes, Integer[] likedBy) {
        this.id = id;
        this.content = content;
        this.author = author;
        this.course = course;
        this.likes = likes;
        this.likedBy = likedBy;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Integer getCourse() {
        return course;
    }

    public void setCourse(Integer course) {
        this.course = course;
    }

    public Integer getLikes() {
        return likes;
    }

    public void setLikes(Integer likes) {
        this.likes = likes;
    }

    public Integer[] getLikedBy() {
        return likedBy;
    }

    public void setLikedBy(Integer[] likedBy) {
        this.likedBy = likedBy;
    }
}
