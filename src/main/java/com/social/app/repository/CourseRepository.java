package com.social.app.repository;

import com.social.app.domain.Course;
import com.social.app.exception.UaBadRequestException;
import com.social.app.exception.UaResourceNotFoundException;

import java.util.List;

public interface CourseRepository {

    List<Course> findAll() throws UaResourceNotFoundException;

    Course findById(Integer courseId) throws UaResourceNotFoundException;

    void updateAttendees(Integer courseId, String attendee) throws UaBadRequestException;

    void removeAttendees(Integer courseId, String attendee) throws UaBadRequestException;
}
