package com.social.app.repository;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.*;

import com.social.app.domain.Comment;
import com.social.app.exception.UaBadRequestException;
import com.social.app.exception.UaResourceNotFoundException;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CommentRepositoryImplTest {

    @Mock
    JdbcTemplate jdbcTemplate;

    @InjectMocks
    CommentRepositoryImpl commentRepository;

    @Test
    void findCommentsByCourseId_ValidInput_ShouldReturnUaResourceNotFoundException() {
        when(jdbcTemplate.query(any(String.class), (Object[]) any(), eq(commentRowMapper)))
                .thenThrow(new RuntimeException());

        assertThrows(UaResourceNotFoundException.class, () -> commentRepository.findCommentsByCourseId(1));
    }

    @Test
    void create_ValidInput_ShouldThrowException() throws UaBadRequestException {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        when(jdbcTemplate.update(any(PreparedStatementCreator.class), eq(keyHolder)))
                .thenReturn(1);

        assertThrows(UaBadRequestException.class, () -> commentRepository.create("Good course", "John", 1, 10));
    }

    private RowMapper<Comment> commentRowMapper = ((rs, rowNum) -> new Comment(rs.getInt("ID"),
            rs.getString("CONTENT"),
            rs.getString("AUTHOR"),
            rs.getInt("LIKES"),
            rs.getInt("COURSE"),
            (Integer[]) rs.getArray("LIKEDBY").getArray()));
}