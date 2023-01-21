package com.social.app.repository;

import com.social.app.domain.User;
import com.social.app.exception.UaAuthException;

public interface UserRepository {

    Integer create(String firstName, String lastName, String email, String password) throws UaAuthException;

    User findByEmailAndPassword(String email, String password) throws UaAuthException;

    Integer getCountByEmail(String email);

    User findById(Integer userId);
}
