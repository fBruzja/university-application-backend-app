package com.social.app.repository;

import com.social.app.domain.Comment;
import com.social.app.domain.Friendship;
import com.social.app.exception.UaBadRequestException;
import com.social.app.exception.UaResourceNotFoundException;
import com.social.app.model.NotificationState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
public class FriendshipRepositoryImpl implements FriendshipRepository {

    private static final String SQL_CREATE_FRIENDSHIP = "INSERT INTO FRIENDSHIP(FRIENDSHIP_ID, USER_ID, FRIEND_ID, STATUS, NOTIFIED)" +
            " VALUES(NEXTVAL('FRIENDSHIP_SEQ', ?, ?, ?, ?)";

    private static final String SQL_FIND_ALL_BY_USER_ID = "SELECT * FROM FRIENDSHIP WHERE" +
            " USER_ID=?";

    private static final String SQL_FIND_ALL_BY_FRIEND_ID = "SELECT * FROM FRIENDSHIP WHERE" +
            " FRIEND_ID=?";

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public Integer createFriendship(Integer userId, Integer friendId) throws UaBadRequestException {
        try {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(con -> {
                PreparedStatement ps = con.prepareStatement(SQL_CREATE_FRIENDSHIP, Statement.RETURN_GENERATED_KEYS);
                ps.setInt(1, userId);
                ps.setInt(2, friendId);
                ps.setString(3, NotificationState.pending.toString());
                ps.setBoolean(4, false);

                return ps;
            }, keyHolder);

            return (Integer) keyHolder.getKeys().get("FRIENDSHIP_ID");
        } catch (Exception e) {
            throw new UaBadRequestException("Invalid request! Cannot create friendship!");
        }
    }

    @Override
    public List<Friendship> findAllByUserId(Integer userId) throws UaResourceNotFoundException {
        try {
            return jdbcTemplate.query(SQL_FIND_ALL_BY_USER_ID, new Object[]{userId}, friendshipRowMapper);
        } catch (Exception e) {
            throw new UaResourceNotFoundException("Friendships not found!");
        }
    }

    @Override
    public List<Friendship> findAllByFriendId(Integer friendId) throws UaResourceNotFoundException {
        try {
            return jdbcTemplate.query(SQL_FIND_ALL_BY_FRIEND_ID, new Object[]{friendId}, friendshipRowMapper);
        } catch (Exception e) {
            throw new UaResourceNotFoundException("Friendships not found!");
        }
    }

    private RowMapper<Friendship> friendshipRowMapper = ((rs, rowNum) -> new Friendship(
            rs.getString("FRIENDSHIP_ID"),
            rs.getInt("USER_ID"),
            rs.getInt("FRIEND_ID"),
            (NotificationState) rs.getObject("FRIENDSHIP_STATUS"),
            rs.getBoolean("")
    ));
}
