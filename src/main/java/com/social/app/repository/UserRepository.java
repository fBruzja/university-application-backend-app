package com.social.app.repository;

import com.social.app.domain.User;
import com.social.app.exception.UaAuthException;
import com.social.app.exception.UaBadRequestException;

public interface UserRepository {

    Integer create(String firstName, String lastName, String email, String password, String major, String minor) throws UaAuthException;

    User findByEmailAndPassword(String email, String password) throws UaAuthException;

    Integer getCountByEmail(String email);

    User findById(Integer userId);

    void updateProfilePicture(String profilePicture, Integer userId) throws UaBadRequestException;

    void updateUserSettings(Boolean notifications, Integer userId) throws UaBadRequestException;

    void addFriend(Integer userId, Integer friendId) throws UaBadRequestException;

    void removeFriend(Integer userId, Integer friendId) throws UaBadRequestException;
}
