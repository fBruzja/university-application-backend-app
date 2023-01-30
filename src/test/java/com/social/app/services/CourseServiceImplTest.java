package com.social.app.services;

import com.social.app.domain.Course;
import com.social.app.exception.UaBadRequestException;
import com.social.app.exception.UaResourceNotFoundException;
import com.social.app.repository.CourseRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CourseServiceImplTest {

    @Mock
    private CourseRepository courseRepository;

    @InjectMocks
    private CourseServiceImpl courseService;

    private List<Course> courses;

    @Before
    public void setUp() {
        courses = new ArrayList<>();
        Course course = new Course();
        course.setCategoryId(1);
        course.setTitle("Java");
        course.setAttendees(new String[]{"user1,user2"});
        courses.add(course);
    }

    @Test
    public void testFetchAllCourses() {
        when(courseRepository.findAll()).thenReturn(courses);
        List<Course> fetchedCourses = courseService.fetchAllCourses();
        assertNotNull(fetchedCourses);
        assertEquals(courses.size(), fetchedCourses.size());
        assertEquals(courses.get(0).getTitle(), fetchedCourses.get(0).getTitle());
    }

    @Test
    public void testFetchCourseById() throws UaResourceNotFoundException {
        when(courseRepository.findById(1)).thenReturn(courses.get(0));
        Course fetchedCourse = courseService.fetchCourseById(1);
        assertNotNull(fetchedCourse);
        assertEquals(courses.get(0).getTitle(), fetchedCourse.getTitle());
    }

    @Test
    public void testUpdateCourseAttendees() throws UaBadRequestException {
        courseService.updateCourseAttendees(1, "user3");
    }

    @Test
    public void testRemoveCourseAttendees() throws UaBadRequestException {
        courseService.removeCourseAttendees(1, "user2");
    }
}