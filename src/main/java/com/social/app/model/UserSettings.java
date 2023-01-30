package com.social.app.model;

public class UserSettings {
    private Boolean notifications;

    public UserSettings() {
    }

    public UserSettings(Boolean notifications) {
        this.notifications = notifications;
    }

    public Boolean getNotifications() {
        return notifications;
    }

    public void setNotifications(Boolean notifications) {
        this.notifications = notifications;
    }
}
