package com.social.app.services;

import com.social.app.domain.Course;
import com.social.app.exception.UaBadRequestException;
import com.social.app.exception.UaResourceNotFoundException;
import com.social.app.model.NotificationState;

import java.util.List;

public interface CourseService {

    List<Course> fetchAllCourses();

    Course fetchCourseById(Integer courseId) throws UaResourceNotFoundException;

    void updateCourseAttendees(Integer categoryId, String attendee) throws UaBadRequestException;

    void removeCourseAttendees(Integer categoryId, String attendee) throws UaBadRequestException;
}
