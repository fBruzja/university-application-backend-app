package com.social.app.domain;

public class Friendship {
    private String friendshipId;
    private Integer userId;
    private Integer friendId;
    private String notificationState;
    private Boolean notified;

    public Friendship(String friendshipId, Integer userId, Integer friendId, String notificationState, Boolean notified) {
        this.friendshipId = friendshipId;
        this.userId = userId;
        this.friendId = friendId;
        this.notificationState = notificationState;
        this.notified = notified;
    }

    public Friendship() {}

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

    public String getNotificationState() {
        return notificationState;
    }

    public void setNotificationState(String notificationState) {
        this.notificationState = notificationState;
    }

    public Boolean getNotified() {
        return notified;
    }

    public void setNotified(Boolean notified) {
        this.notified = notified;
    }
}
