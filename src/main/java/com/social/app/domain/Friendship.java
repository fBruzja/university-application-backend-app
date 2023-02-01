package com.social.app.domain;

import com.social.app.model.NotificationState;

public class Friendship {
    private String friendshipId;
    private Integer userId;
    private Integer friendId;
    private NotificationState notificationState;
    private Boolean notified;

    public Friendship(String friendshipId, Integer userId, Integer friendId, NotificationState notificationState, Boolean notified) {
        this.friendshipId = friendshipId;
        this.userId = userId;
        this.friendId = friendId;
        this.notificationState = notificationState;
        this.notified = notified;
    }

    public String getFriendshipId() {
        return friendshipId;
    }

    public void setFriendshipId(String friendshipId) {
        this.friendshipId = friendshipId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getFriendId() {
        return friendId;
    }

    public void setFriendId(Integer friendId) {
        this.friendId = friendId;
    }

    public NotificationState getNotificationState() {
        return notificationState;
    }

    public void setNotificationState(NotificationState notificationState) {
        this.notificationState = notificationState;
    }

    public Boolean getNotified() {
        return notified;
    }

    public void setNotified(Boolean notified) {
        this.notified = notified;
    }
}
