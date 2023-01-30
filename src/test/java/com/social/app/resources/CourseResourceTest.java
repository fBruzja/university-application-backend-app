package com.social.app.resources;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.social.app.domain.Course;
import com.social.app.services.CourseService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

class CourseResourceTest {

    @InjectMocks
    CourseResource courseResource;

    @Mock
    CourseService courseService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testGetAllCategories() {
        List<Course> courses = Arrays.asList(new Course(1, "Test Course 1"),
                new Course(2, "Test Course 2"));
        when(courseService.fetchAllCourses()).thenReturn(courses);

        ResponseEntity<List<Course>> response = courseResource.getAllCategories();
        List<Course> responseCourses = response.getBody();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, responseCourses.size());
        assertEquals("Test Course 1", responseCourses.get(0).getTitle());
        assertEquals("Test Course 2", responseCourses.get(1).getTitle());
        verify(courseService, times(1)).fetchAllCourses();
    }

    @Test
    void testGetCourseById() {
        Course course = new Course(1, "Test Course");
        when(courseService.fetchCourseById(1)).thenReturn(course);

        ResponseEntity<Course> response = courseResource.getCourseById(1);
        Course responseCourse = response.getBody();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, responseCourse.getCategoryId());
        assertEquals("Test Course", responseCourse.getTitle());
        verify(courseService, times(1)).fetchCourseById(1);
    }

    @Test
    void testUpdateCourseAttendees() {
        doNothing().when(courseService).updateCourseAttendees(1, "attendee1");

        ResponseEntity<Map<String, Boolean>> response = courseResource.updateCourseAttendees(1, "attendee1");
        Map<String, Boolean> responseMap = response.getBody();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(responseMap.get("success"));
        verify(courseService, times(1)).updateCourseAttendees(1, "attendee1");
    }

    @Test
    public void removeCourseAttendeesTest() {
        ResponseEntity<Map<String, Boolean>> response = courseResource.removeCourseAttendees(null, 1, "test");
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(courseService).removeCourseAttendees(anyInt(), anyString());
    }
}