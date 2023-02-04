package com.social.app.repository;

import com.social.app.domain.Friendship;
import com.social.app.exception.UaBadRequestException;
import com.social.app.exception.UaResourceNotFoundException;
import com.social.app.model.NotificationState;

import java.util.List;

public interface FriendshipRepository {

    List<Friendship> findAllByUserId(Integer userId) throws UaResourceNotFoundException;

    List<Friendship> findAllByFriendId(Integer friendId) throws UaResourceNotFoundException;

    Integer createFriendship(Integer userId, Integer friendId) throws UaBadRequestException;

    List<Friendship> retrieveSpecificFriendship(Integer userId, Integer friendId) throws UaResourceNotFoundException;

    void setStatusToNotified(Integer userId, Integer friendId) throws UaBadRequestException;

    void changeFriendRequestStatus(Integer userId, Integer friendId, String notificationState) throws UaBadRequestException;
}
