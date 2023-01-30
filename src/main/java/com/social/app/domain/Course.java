package com.social.app.domain;

public class Course {
    public Course(int categoryId, String title) {
        this.categoryId = categoryId;
        this.title = title;
    }

    public Course() {

    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCourseTime() {
        return courseTime;
    }

    public void setCourseTime(String courseTime) {
        this.courseTime = courseTime;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String[] getAttendees() {
        return attendees;
    }

    public void setAttendees(String[] attendees) {
        this.attendees = attendees;
    }

    private Integer categoryId;
    private String title;
    private String description;
    private String courseTime;
    private String location;
    private String[] attendees;

    public Course(Integer categoryId, String title, String description, String courseTime, String location, String[] attendees) {
        this.categoryId = categoryId;
        this.title = title;
        this.description = description;
        this.courseTime = courseTime;
        this.location = location;
        this.attendees = attendees;
    }
}
