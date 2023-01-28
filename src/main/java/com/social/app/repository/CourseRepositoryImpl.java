package com.social.app.repository;

import com.social.app.domain.Course;
import com.social.app.exception.UaBadRequestException;
import com.social.app.exception.UaResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CourseRepositoryImpl implements CourseRepository{

    private static final String SQL_FIND_ALL = "SELECT * FROM UA_CATEGORIES";
    private static final String SQL_FIND_BY_ID = "SELECT CATEGORY_ID, TITLE, DESCRIPTION, COURSE_TIME, LOCATION, ATTENDEES FROM UA_CATEGORIES WHERE CATEGORY_ID=?";
    private static final String SQL_UPDATE_ATTENDEES = "UPDATE UA_CATEGORIES SET ATTENDEES = array_append(ATTENDEES, ?) WHERE CATEGORY_ID=?";
    private static final String SQL_REMOVE_ATTENDEES = "UPDATE UA_CATEGORIES SET ATTENDEES = array_remove(ATTENDEES, ?) WHERE CATEGORY_ID=?";

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public List<Course> findAll() throws UaResourceNotFoundException {
        return jdbcTemplate.query(SQL_FIND_ALL, new Object[]{}, courseRowMapper);
    }

    @Override
    public Course findById(Integer courseId) throws UaResourceNotFoundException {
        try {
            return jdbcTemplate.queryForObject(SQL_FIND_BY_ID, new Object[]{courseId}, courseRowMapper);
        } catch (Exception e) {
            throw new UaResourceNotFoundException("Course not found!");
        }
    }

    @Override
    public void updateAttendees(Integer courseId, String attendee) throws UaBadRequestException {
        try {
            jdbcTemplate.update(SQL_UPDATE_ATTENDEES, attendee, courseId);
        } catch (Exception e) {
            throw new UaBadRequestException("Invalid request!");
        }
    }

    @Override
    public void removeAttendees(Integer courseId, String attendee) throws UaBadRequestException {
        try {
            jdbcTemplate.update(SQL_REMOVE_ATTENDEES, attendee, courseId);
        } catch (Exception e) {
            throw new UaBadRequestException("Invalid request!");
        }
    }

    private RowMapper<Course> courseRowMapper = ((rs, rowNum) -> new Course(rs.getInt("CATEGORY_ID"),
            rs.getString("TITLE"),
            rs.getString("DESCRIPTION"),
            rs.getString("COURSE_TIME"),
            rs.getString("LOCATION"),
            (String[])rs.getArray("ATTENDEES").getArray()));
}
