package com.social.app.services;

import com.social.app.domain.User;
import com.social.app.exception.UaAuthException;
import com.social.app.exception.UaBadRequestException;
import com.social.app.exception.UaResourceNotFoundException;

public interface UserService {

    User validateUser(String email, String password) throws UaAuthException;

    User registerUser(String firstName, String lastName, String email, String password, String major, String minor) throws UaAuthException;

    User getUserById(Integer userId) throws UaResourceNotFoundException;

    void updateUserProfilePicture(String profilePicture, Integer userId) throws UaBadRequestException;

    void updateUserSettings(Boolean notifications, Integer userId) throws UaBadRequestException;
}
