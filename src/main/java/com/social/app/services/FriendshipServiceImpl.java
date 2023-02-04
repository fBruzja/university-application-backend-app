package com.social.app.services;

import com.social.app.domain.Friendship;
import com.social.app.exception.UaBadRequestException;
import com.social.app.exception.UaResourceNotFoundException;
import com.social.app.model.NotificationState;
import com.social.app.repository.FriendshipRepository;
import com.social.app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class FriendshipServiceImpl implements FriendshipService {

    @Autowired
    FriendshipRepository friendshipRepository;

    @Autowired
    UserRepository userRepository;

    @Override
    public List<Friendship> findAllByUserId(Integer userId) throws UaResourceNotFoundException {
        return friendshipRepository.findAllByUserId(userId);
    }

    @Override
    public List<Friendship> findAllByFriendId(Integer friendId) throws UaResourceNotFoundException {
        return friendshipRepository.findAllByFriendId(friendId);
    }

    @Override
    public Integer createFriendship(Integer userId, Integer friendId) throws UaBadRequestException {
        return friendshipRepository.createFriendship(userId, friendId);
    }

    @Override
    public List<Friendship> retrieveSpecificFriendship(Integer userId, Integer friendId) throws UaResourceNotFoundException {
        return friendshipRepository.retrieveSpecificFriendship(userId, friendId);
    }

    @Override
    public Boolean areTwoUsersFriends(Integer userId1, Integer userId2) throws UaBadRequestException {
        List<Friendship> friendship1 = friendshipRepository.retrieveSpecificFriendship(userId1, userId2);
        List<Friendship> friendship2 = friendshipRepository.retrieveSpecificFriendship(userId2, userId1);

        return friendship1.size() > 0 || friendship2.size() > 0;
    }

    @Override
    public Integer countNotificationsForAnUser(Integer userId) throws UaBadRequestException {
        List<Friendship> friendshipsPending = friendshipRepository.findAllByFriendId(userId);
        int friendshipsPendingThatShouldBeNotifiable = friendshipsPending
                .stream()
                .filter(friendship -> !friendship.getNotified())
                .toList().size();

        int rejectedFriendShipsThatShouldBeNotifiable = friendshipRepository.findAllByUserId(userId)
                .stream()
                .filter(friendship -> !friendship.getNotified() && !friendship.getNotificationState().equals("pending"))
                .toList().size();

        return friendshipsPendingThatShouldBeNotifiable + rejectedFriendShipsThatShouldBeNotifiable;
    }

    @Override
    public List<Friendship> getFriendshipsThatRequireNotificationAction(Integer userId) throws UaResourceNotFoundException {
        List<Friendship> friendships = friendshipRepository.findAllByUserId(userId);

        return friendships.stream()
                .filter(friendship -> !friendship.getNotified())
                .collect(Collectors.toList());
    }

    @Override
    public List<Friendship> getPendingFriendshipRequests(Integer userId) throws UaResourceNotFoundException {
        List<Friendship> friendships = friendshipRepository.findAllByFriendId(userId);

        return friendships.stream()
                .filter(friendship -> !friendship.getNotified()
                        && friendship.getNotificationState().equals("pending"))
                .collect(Collectors.toList());
    }

    @Override
    public void setStatusToNotified(Integer userId, Integer friendId) throws UaBadRequestException {
        friendshipRepository.setStatusToNotified(userId, friendId);
    }

    @Override
    public void changeFriendRequestStatus(Integer userId, Integer friendId, String notificationState) throws UaBadRequestException {
        friendshipRepository.changeFriendRequestStatus(userId, friendId, notificationState);
    }

    @Override
    public void rejectFriendRequest(Integer userId, Integer friendId) throws UaBadRequestException {
//        friendshipRepository.setStatusToNotified(userId, friendId);
        friendshipRepository.changeFriendRequestStatus(userId, friendId, "rejected");
    }

    @Override
    public void acceptFriendRequest(Integer userId, Integer friendId) throws UaBadRequestException {
//        friendshipRepository.setStatusToNotified(userId, friendId);
        friendshipRepository.changeFriendRequestStatus(userId, friendId, "accepted");
        userRepository.addFriend(userId, friendId);
    }
}
