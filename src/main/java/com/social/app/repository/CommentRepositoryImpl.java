package com.social.app.repository;

import com.social.app.domain.Comment;
import com.social.app.exception.UaBadRequestException;
import com.social.app.exception.UaResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Array;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
public class CommentRepositoryImpl implements CommentRepository {

    private static final String SQL_FIND_COMMENTS_BY_COURSE_ID = "SELECT * FROM UA_COMMENTS WHERE COURSE=?";
    private static final String SQL_CREATE_COMMENT = "INSERT INTO UA_COMMENTS(ID, CONTENT, AUTHOR, LIKES, COURSE, LIKEDBY) " +
            "VALUES(NEXTVAL('UA_COMMENTS_SEQ'), ?, ?, ?, ?, ?)";
    private static final String SQL_LIKE_COMMENT = "UPDATE UA_COMMENTS SET LIKES = LIKES + 1 WHERE ID=?";
    private static final String SQL_UPDATE_LIKED_BY_COMMENT = "UPDATE UA_COMMENTS SET LIKEDBY=array_append(LIKEDBY, ?)" +
            " WHERE ID=?";

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public List<Comment> findCommentsByCourseId(Integer courseId) throws UaResourceNotFoundException {
        try {
            return jdbcTemplate.query(SQL_FIND_COMMENTS_BY_COURSE_ID, new Object[]{courseId}, commentRowMapper);
        } catch (Exception e) {
            throw new UaResourceNotFoundException("Comments not found!");
        }
    }

    @Override
    public Integer create(String content, String author, String course, Integer likes) throws UaBadRequestException {
        try {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(con -> {
                Array emptyArray = con.createArrayOf("integer", new Object[]{});
                PreparedStatement ps = con.prepareStatement(SQL_CREATE_COMMENT, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, content);
                ps.setString(2, author);
                ps.setInt(3, likes);
                ps.setInt(4, Integer.parseInt(course));
                ps.setArray(5, emptyArray);

                return ps;
            }, keyHolder);

            return (Integer) keyHolder.getKeys().get("ID");
        } catch (Exception e) {
            throw new UaBadRequestException("Invalid request! Could not create comment!");
        }
    }

    @Override
    public void likeComment(Integer userId, Integer commentId) throws UaBadRequestException {
        try {
            jdbcTemplate.update(SQL_LIKE_COMMENT, commentId);
            jdbcTemplate.update(SQL_UPDATE_LIKED_BY_COMMENT, userId, commentId);
        } catch (Exception e) {
            throw new UaBadRequestException("Invalid request!");
        }
    }

    private RowMapper<Comment> commentRowMapper = ((rs, rowNum) -> new Comment(rs.getInt("ID"),
            rs.getString("CONTENT"),
            rs.getString("AUTHOR"),
            rs.getInt("COURSE"),
            rs.getInt("LIKES"),
            (Integer[]) rs.getArray("LIKEDBY").getArray()));
}
