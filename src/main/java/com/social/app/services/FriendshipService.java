package com.social.app.services;

import com.social.app.domain.Friendship;
import com.social.app.exception.UaBadRequestException;
import com.social.app.exception.UaResourceNotFoundException;
import com.social.app.model.NotificationState;

import java.util.List;

public interface FriendshipService {

    List<Friendship> findAllByUserId(Integer userId) throws UaResourceNotFoundException;

    List<Friendship> findAllByFriendId(Integer friendId) throws UaResourceNotFoundException;

    Integer createFriendship(Integer userId, Integer friendId) throws UaBadRequestException;

    List<Friendship> retrieveSpecificFriendship(Integer userId, Integer friendId) throws UaResourceNotFoundException;

    Boolean areTwoUsersFriends(Integer userId1, Integer userId2) throws UaBadRequestException;

    Integer countNotificationsForAnUser(Integer userId) throws UaBadRequestException;

    List<Friendship> getFriendshipsThatRequireNotificationAction(Integer userId) throws UaResourceNotFoundException;

    void setStatusToNotified(Integer userId, Integer friendId) throws UaBadRequestException;

    void changeFriendRequestStatus(Integer userId, Integer friendId, String notificationState) throws UaBadRequestException;

    void rejectFriendRequest(Integer userId, Integer friendId) throws UaBadRequestException;

    void acceptFriendRequest(Integer userId, Integer friendId) throws UaBadRequestException;

    List<Friendship> getPendingFriendshipRequests(Integer userId) throws UaResourceNotFoundException;
}
