package com.social.app.repository;

import com.social.app.domain.User;
import com.social.app.exception.UaAuthException;
import com.social.app.exception.UaBadRequestException;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Array;
import java.sql.PreparedStatement;
import java.sql.Statement;

@Repository
public class UserRepositoryImpl implements UserRepository {

    private static final String SQL_CREATE = "INSERT INTO UA_USERS(USER_ID, FIRST_NAME, LAST_NAME, EMAIL," +
            " PASSWORD, MAJOR, MINOR, COURSES, PROFILE_PICTURE, NOTIFICATIONS) VALUES(NEXTVAL('UA_USERS_SEQ'), ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    private static final String SQL_COUNT_BY_EMAIL = "SELECT COUNT(*) FROM UA_USERS WHERE EMAIL = ?";

    private static final String SQL_FIND_BY_EMAIL = "SELECT USER_ID, FIRST_NAME, LAST_NAME, EMAIL, PASSWORD, MAJOR, MINOR, COURSES, PROFILE_PICTURE, NOTIFICATIONS " +
            "FROM UA_USERS WHERE EMAIL = ?";

    private static final String SQL_FIND_BY_ID = "SELECT USER_ID, FIRST_NAME, LAST_NAME, EMAIL, PASSWORD, MAJOR, MINOR, COURSES, PROFILE_PICTURE, NOTIFICATIONS " +
            "FROM UA_USERS WHERE USER_ID = ?";

    private static final String SQL_UPDATE_PROFILE_PICTURE = "UPDATE UA_USERS SET PROFILE_PICTURE = ? " +
            " WHERE USER_ID=?";
    private static final String SQL_UPDATE_USER_SETTINGS = "UPDATE UA_USERS SET NOTIFICATIONS = ? " +
            " WHERE USER_ID=?";

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public Integer create(String firstName, String lastName, String email, String password, String major, String minor) throws UaAuthException {
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt(10));
        try {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(con -> {
                Array emptyArray = con.createArrayOf("text", new Object[]{});
                PreparedStatement ps = con.prepareStatement(SQL_CREATE, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, firstName);
                ps.setString(2, lastName);
                ps.setString(3, email);
                ps.setString(4, hashedPassword);
                ps.setString(5, major);
                ps.setString(6, minor);
                ps.setArray(7, emptyArray);
                ps.setString(8, "");
                ps.setBoolean(9, false);

                return ps;
            }, keyHolder);
            return (Integer) keyHolder.getKeys().get("USER_ID");
        } catch (Exception e) {
            throw new UaAuthException("Invalid details. Failed to create account");
        }
    }

    @Override
    public User findByEmailAndPassword(String email, String password) throws UaAuthException {
        try {
            User user = jdbcTemplate.queryForObject(SQL_FIND_BY_EMAIL, new Object[]{email}, userRowMapper);
            if(!BCrypt.checkpw(password, user.getPassword()))
                throw new UaAuthException("Invalid email/password");
            return user;
        }catch (EmptyResultDataAccessException e) {
            throw new UaAuthException("Invalid email/password");
        }
    }

    @Override
    public Integer getCountByEmail(String email) {
        return jdbcTemplate.queryForObject(SQL_COUNT_BY_EMAIL, new Object[]{email}, Integer.class);
    }

    @Override
    public User findById(Integer userId) {
        return jdbcTemplate.queryForObject(SQL_FIND_BY_ID, new Object[]{userId}, userRowMapper);
    }

    @Override
    public void updateProfilePicture(String profilePicture, Integer userId) throws UaBadRequestException {
        try {
            jdbcTemplate.update(SQL_UPDATE_PROFILE_PICTURE, profilePicture, userId);
        } catch (Exception e) {
            throw new UaBadRequestException("Invalid Request!");
        }
    }

    @Override
    public void updateUserSettings(Boolean notifications, Integer userId) throws UaBadRequestException {
        try {
            jdbcTemplate.update(SQL_UPDATE_USER_SETTINGS, notifications, userId);
        } catch (Exception e) {
            throw new UaBadRequestException("Invalid request!");
        }
    }

    private RowMapper<User> userRowMapper = ((rs, rowNum) -> new User(
            rs.getInt("USER_ID"),
            rs.getString("FIRST_NAME"),
            rs.getString("LAST_NAME"),
            rs.getString("EMAIL"),
            rs.getString("PASSWORD"),
            rs.getString("MAJOR"),
            rs.getString("MINOR"),
            (String[]) rs.getArray("COURSES").getArray(),
            rs.getString("PROFILE_PICTURE"),
            rs.getBoolean("NOTIFICATIONS"))
    );
}
