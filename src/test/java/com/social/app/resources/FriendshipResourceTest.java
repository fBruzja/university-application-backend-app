package com.social.app.resources;

import com.social.app.domain.Friendship;
import com.social.app.resources.FriendshipResource;
import com.social.app.services.FriendshipService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.*;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class FriendshipResourceTest {

    @Mock
    FriendshipService friendshipService;

    @InjectMocks
    FriendshipResource friendshipResource;

    private Map<String, Object> friendshipMap;


    private Friendship friendship;
    private List<Friendship> friendships;

    @Before
    public void setUp() {
        friendshipMap = new HashMap<>();
        friendshipMap.put("userId", 1);
        friendshipMap.put("friendId", 2);

        friendship = new Friendship();
        friendship.setFriendshipId("1");
        friendship.setUserId(2);
        friendship.setFriendId(3);
        friendship.setNotificationState("pending");

        friendships = new ArrayList<>();
        friendships.add(friendship);
    }

    @Test
    public void createFriendshipTest() {
        when(friendshipService.createFriendship(1, 2)).thenReturn(3);
        ResponseEntity<Integer> result = friendshipResource.createFriendship(friendshipMap);
        assertEquals(3, (int) result.getBody());
        verify(friendshipService, times(1)).createFriendship(1, 2);
    }

    @Test
    public void rejectFriendRequestTest() {
        friendshipResource.rejectFriendRequest(1, 2);
        verify(friendshipService, times(1)).rejectFriendRequest(1, 2);
    }

    @Test
    public void acceptFriendRequestTest() {
        friendshipResource.acceptFriendRequest(1, 2);
        verify(friendshipService, times(1)).acceptFriendRequest(1, 2);
    }

    @Test
    public void findAllByUserIdTest() {
        List<Friendship> expected = Arrays.asList(new Friendship(), new Friendship());
        when(friendshipService.findAllByUserId(1)).thenReturn(expected);
        ResponseEntity<List<Friendship>> result = friendshipResource.findAllByUserId(1);
        assertEquals(expected, result.getBody());
        verify(friendshipService, times(1)).findAllByUserId(1);
    }

    @Test
    public void findAllByFriendIdTest() {
        List<Friendship> expected = Arrays.asList(new Friendship(), new Friendship());
        when(friendshipService.findAllByFriendId(1)).thenReturn(expected);
        ResponseEntity<List<Friendship>> result = friendshipResource.findAllByFriendId(1);
        assertEquals(expected, result.getBody());
        verify(friendshipService, times(1)).findAllByFriendId(1);
    }

    @Test
    public void findSpecificFriendship_shouldReturnAListOfFriendships() {
        List<Friendship> expectedFriendships = new ArrayList<>();
        expectedFriendships.add(new Friendship());
        when(friendshipService.retrieveSpecificFriendship(1, 2)).thenReturn(expectedFriendships);

        ResponseEntity<List<Friendship>> result = friendshipResource.findSpecificFriendship(1, 2);

        assertEquals(expectedFriendships, result.getBody());
        assertEquals(200, result.getStatusCodeValue());
    }

    @Test
    public void areUsersInAFriendStatus_shouldReturnABoolean() {
        when(friendshipService.areTwoUsersFriends(1, 2)).thenReturn(true);

        ResponseEntity<Boolean> result = friendshipResource.areUsersInAFriendStatus(1, 2);

        assertTrue(result.getBody());
        assertEquals(200, result.getStatusCodeValue());
    }

    @Test
    public void countFriendshipNotifications_shouldReturnAnInteger() {
        when(friendshipService.countNotificationsForAnUser(1)).thenReturn(1);

        ResponseEntity<Integer> result = friendshipResource.countFriendshipNotifications(1);

        assertEquals(1, result.getBody().intValue());
        assertEquals(200, result.getStatusCodeValue());
    }

    @Test
    public void getActiveFriendships_shouldReturnAListOfFriendships() {
        List<Friendship> expectedFriendships = new ArrayList<>();
        expectedFriendships.add(new Friendship());
        when(friendshipService.getFriendshipsThatRequireNotificationAction(1)).thenReturn(expectedFriendships);

        ResponseEntity<List<Friendship>> result = friendshipResource.getActiveFriendships(1);

        assertEquals(expectedFriendships, result.getBody());
        assertEquals(200, result.getStatusCodeValue());
    }

    @Test
    public void testGetActivePendingFriendships() {
        when(friendshipService.getPendingFriendshipRequests(2)).thenReturn(friendships);

        ResponseEntity<List<Friendship>> response = friendshipResource.getActivePendingFriendships(2);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(friendships, response.getBody());
    }

    @Test
    public void testSetStatusToNotified() {
        Map<String, Boolean> expectedMap = new HashMap<>();
        expectedMap.put("success", true);

        ResponseEntity<Map<String, Boolean>> response = friendshipResource.setStatusToNotified(2, 3);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedMap, response.getBody());
    }

    @Test
    public void testChangeFriendshipRequest() {
        Map<String, Object> friendshipMap = new HashMap<>();
        friendshipMap.put("notificationState", "accepted");

        Map<String, Boolean> expectedMap = new HashMap<>();
        expectedMap.put("success", true);

        ResponseEntity<Map<String, Boolean>> response = friendshipResource.changeFriendshipRequest(friendshipMap, 2, 3);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedMap, response.getBody());
    }
}
