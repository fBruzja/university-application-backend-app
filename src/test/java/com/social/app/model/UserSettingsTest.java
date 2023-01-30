package com.social.app.model;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class UserSettingsTest {
    @Test
    public void testGetNotifications() {
        UserSettings userSettings = new UserSettings(true);
        assertEquals(true, userSettings.getNotifications());
    }

    @Test
    public void testSetNotifications() {
        UserSettings userSettings = new UserSettings();
        userSettings.setNotifications(false);
        assertEquals(false, userSettings.getNotifications());
    }
}