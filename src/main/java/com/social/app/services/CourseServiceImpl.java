package com.social.app.services;

import com.social.app.domain.Course;
import com.social.app.exception.UaBadRequestException;
import com.social.app.exception.UaResourceNotFoundException;
import com.social.app.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional
public class CourseServiceImpl implements CourseService{

    @Autowired
    CourseRepository courseRepository;

    @Override
    public List<Course> fetchAllCourses() {
        return courseRepository.findAll();
    }

    @Override
    public Course fetchCourseById(Integer courseId) throws UaResourceNotFoundException {
        return courseRepository.findById(courseId);
    }

    @Override
    public void updateCourseAttendees(Integer categoryId, String attendee) throws UaBadRequestException {
        courseRepository.updateAttendees(categoryId, attendee);
    }

    @Override
    public void removeCourseAttendees(Integer categoryId, String attendee) throws UaBadRequestException {
        courseRepository.removeAttendees(categoryId, attendee);
    }
}
