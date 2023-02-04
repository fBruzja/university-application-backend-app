package com.social.app.domain;

import org.junit.Test;
import static org.junit.Assert.*;

public class FriendshipTest {

    @Test
    public void testFriendshipCreation() {
        Friendship friendship = new Friendship("f1", 1, 2, "pending", false);
        assertEquals("f1", friendship.getFriendshipId());
        assertEquals(Integer.valueOf(1), friendship.getUserId());
        assertEquals(Integer.valueOf(2), friendship.getFriendId());
        assertEquals("pending", friendship.getNotificationState());
        assertEquals(false, friendship.getNotified());
    }

    @Test
    public void testFriendshipSetters() {
        Friendship friendship = new Friendship();
        friendship.setFriendshipId("f2");
        friendship.setUserId(3);
        friendship.setFriendId(4);
        friendship.setNotificationState("accepted");
        friendship.setNotified(true);
        assertEquals("f2", friendship.getFriendshipId());
        assertEquals(Integer.valueOf(3), friendship.getUserId());
        assertEquals(Integer.valueOf(4), friendship.getFriendId());
        assertEquals("accepted", friendship.getNotificationState());
        assertEquals(true, friendship.getNotified());
    }
}
