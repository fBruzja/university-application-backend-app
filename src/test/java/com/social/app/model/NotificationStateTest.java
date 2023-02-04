package com.social.app.model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class NotificationStateTest {

    @Test
    public void testNotificationStateEnum() {
        NotificationState state = NotificationState.accepted;
        assertEquals("accepted", state.toString());

        state = NotificationState.pending;
        assertEquals("pending", state.toString());

        state = NotificationState.rejected;
        assertEquals("rejected", state.toString());
    }
}
