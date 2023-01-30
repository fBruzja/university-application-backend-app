package com.social.app.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.core.JdbcTemplate;

import com.social.app.domain.Course;
import com.social.app.exception.UaBadRequestException;
import com.social.app.exception.UaResourceNotFoundException;
import org.springframework.jdbc.core.RowMapper;

class CourseRepositoryImplTest {

    private CourseRepositoryImpl courseRepositoryImpl;

    @Mock
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        courseRepositoryImpl = new CourseRepositoryImpl();
        courseRepositoryImpl.jdbcTemplate = jdbcTemplate;
    }

    @Test
    void testFindAll() throws UaResourceNotFoundException {
        List<Course> expectedCourses = Arrays.asList(
                new Course(1, "course1", "description1", "time1", "location1", new String[]{"1", "2"}),
                new Course(2, "course2", "description2", "time2", "location2", new String[]{"3", "4"})
        );
        doReturn(expectedCourses).when(jdbcTemplate).query(eq(CourseRepositoryImpl.SQL_FIND_ALL), (Object[]) any(), any(RowMapper.class));

        List<Course> actualCourses = courseRepositoryImpl.findAll();

        assertEquals(expectedCourses, actualCourses);
        verify(jdbcTemplate).query(eq(CourseRepositoryImpl.SQL_FIND_ALL), eq(new Object[]{}), any(RowMapper.class));
    }

    @Test
    void testFindById() throws UaResourceNotFoundException {
        Course expectedCourse = new Course(1, "course1", "description1", "time1", "location1", new String[]{"1", "2"});
        doReturn(expectedCourse).when(jdbcTemplate).queryForObject(eq(CourseRepositoryImpl.SQL_FIND_BY_ID), eq(new Object[]{1}), any(RowMapper.class));

        Course actualCourse = courseRepositoryImpl.findById(1);

        assertEquals(expectedCourse, actualCourse);
        verify(jdbcTemplate).queryForObject(eq(CourseRepositoryImpl.SQL_FIND_BY_ID), eq(new Object[]{1}), any(RowMapper.class));
    }

    @Test
    void findById_whenCourseDoesNotExist_throwsUaResourceNotFoundException() {
        when(jdbcTemplate.queryForObject(eq(CourseRepositoryImpl.SQL_FIND_BY_ID), eq(new Object[]{1}), any(RowMapper.class))).thenReturn(null);
        when(jdbcTemplate.queryForObject(eq(CourseRepositoryImpl.SQL_FIND_BY_ID), eq(new Object[]{1}), any(RowMapper.class))).thenThrow(UaResourceNotFoundException.class);

        assertThrows(UaResourceNotFoundException.class, () -> {
            courseRepositoryImpl.findById(1);
        });
    }

    @Test
    public void testUpdateAttendees() throws SQLException, UaBadRequestException {
        when(jdbcTemplate.update(CourseRepositoryImpl.SQL_UPDATE_ATTENDEES, "1", 1))
                .thenReturn(1);
        when(jdbcTemplate.update(CourseRepositoryImpl.SQL_UPDATE_COURSES, "1", 1)).thenReturn(1);

        courseRepositoryImpl.updateAttendees(1, "1");
    }

    @Test
    public void testUpdateAttendeesWithBadRequestException() throws UaBadRequestException {
        when(jdbcTemplate.update(CourseRepositoryImpl.SQL_UPDATE_ATTENDEES, "1", 1))
                .thenThrow(new UaBadRequestException("Invalid request!"));

        assertThrows(UaBadRequestException.class, () -> courseRepositoryImpl.updateAttendees(1, "1"));
    }

    @Test
    public void testRemoveAttendees() throws UaBadRequestException {
        when(jdbcTemplate.update(CourseRepositoryImpl.SQL_REMOVE_ATTENDEES, "1", 1))
                .thenReturn(1);
        when(jdbcTemplate.update(CourseRepositoryImpl.SQL_REMOVE_COURSE_FROM_USER, "1", 1))
                .thenReturn(1);

        courseRepositoryImpl.removeAttendees(1, "1");
    }
}
