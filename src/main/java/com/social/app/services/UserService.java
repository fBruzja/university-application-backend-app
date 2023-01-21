package com.social.app.services;

import com.social.app.domain.User;
import com.social.app.exception.UaAuthException;

public interface UserService {

    User validateUser(String email, String password) throws UaAuthException;

    User registerUser(String firstName, String lastName, String email, String password) throws UaAuthException;


}
