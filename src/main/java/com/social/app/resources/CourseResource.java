package com.social.app.resources;

import com.social.app.domain.Course;
import com.social.app.services.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/courses")
public class CourseResource {

    @Autowired
    CourseService courseService;

    @GetMapping("")
    public ResponseEntity<List<Course>> getAllCategories() {
        List<Course> courses = courseService.fetchAllCourses();
        return new ResponseEntity<>(courses, HttpStatus.OK);
    }

    @GetMapping("/{courseId}")
    public ResponseEntity<Course> getCourseById(@PathVariable("courseId") Integer courseId) {
        Course course = courseService.fetchCourseById(courseId);
        return new ResponseEntity<>(course, HttpStatus.OK);
    }

    @PutMapping("/{courseId}/{attendee}")
    public ResponseEntity<Map<String, Boolean>> updateCourseAttendees(@PathVariable("courseId") Integer courseId,
                                                                      @PathVariable("attendee") String attendee) {
        courseService.updateCourseAttendees(courseId, attendee);
        Map<String, Boolean> map = new HashMap<>();
        map.put("success", true);
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @PutMapping("/remove-attendee/{courseId}/{attendee}")
    public ResponseEntity<Map<String, Boolean>> removeCourseAttendees(HttpServletRequest request,
                                                                      @PathVariable("courseId") Integer courseId,
                                                                      @PathVariable("attendee") String attendee) {
        courseService.removeCourseAttendees(courseId, attendee);
        Map<String, Boolean> map = new HashMap<>();
        map.put("success", true);
        return new ResponseEntity<>(map, HttpStatus.OK);
    }
}
