package com.social.app.repository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.social.app.domain.User;
import com.social.app.exception.UaAuthException;
import com.social.app.repository.UserRepositoryImpl;
import org.junit.jupiter.api.Test;
import org.mindrot.jbcrypt.BCrypt;
import org.mockito.Mockito;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

class UserRepositoryImplTest {
    private UserRepositoryImpl userRepository = new UserRepositoryImpl();
    private JdbcTemplate jdbcTemplate = mock(JdbcTemplate.class);
    private RowMapper<User> userRowMapper = mock(RowMapper.class);

    @Test
    public void testCreate_UaAuthException() throws UaAuthException {
        String firstName = "John";
        String lastName = "Doe";
        String email = "johndoe@example.com";
        String password = "password";
        String major = "Computer Science";
        String minor = "Mathematics";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        userRepository.jdbcTemplate = jdbcTemplate;
        Mockito.doThrow(new UaAuthException("Invalid details. Failed to create account")).when(jdbcTemplate).update(any(), Mockito.eq(keyHolder));

        assertThrows(UaAuthException.class, () -> {
            userRepository.create(firstName, lastName, email, password, major, minor);
        });
    }
}
