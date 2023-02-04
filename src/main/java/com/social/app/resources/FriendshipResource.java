package com.social.app.resources;

import com.social.app.domain.Friendship;
import com.social.app.services.FriendshipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/friendship")
public class FriendshipResource {

    @Autowired
    FriendshipService friendshipService;

    @PostMapping("/add-friendship")
    public ResponseEntity<Integer> createFriendship(@RequestBody Map<String, Object> friendshipMap) {
        Integer userId = (Integer) friendshipMap.get("userId");
        Integer friendId = (Integer) friendshipMap.get("friendId");

        Integer friendship = friendshipService.createFriendship(userId, friendId);

        return new ResponseEntity<>(friendship, HttpStatus.OK);
    }

    @PutMapping("/friends/request/reject/{userId}/{friendId}")
    public ResponseEntity<Map<String, Boolean>> rejectFriendRequest(@PathVariable("userId") Integer userId,
                                                                    @PathVariable("friendId") Integer friendId) {
        friendshipService.rejectFriendRequest(userId, friendId);
        Map<String, Boolean> map = new HashMap<>();
        map.put("success", true);
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @PutMapping("/friends/request/accept/{userId}/{friendId}")
    public ResponseEntity<Map<String, Boolean>> acceptFriendRequest(@PathVariable("userId") Integer userId,
                                                                    @PathVariable("friendId") Integer friendId) {
        friendshipService.acceptFriendRequest(userId, friendId);
        Map<String, Boolean> map = new HashMap<>();
        map.put("success", true);
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @GetMapping("/user-id/{userId}")
    public ResponseEntity<List<Friendship>> findAllByUserId(@PathVariable("userId") Integer userId) {
        List<Friendship> friendships = friendshipService.findAllByUserId(userId);
        return new ResponseEntity<>(friendships, HttpStatus.OK);
    }

    @GetMapping("/friend-id/{friendId}")
    public ResponseEntity<List<Friendship>> findAllByFriendId(@PathVariable("friendId") Integer friendId) {
        List<Friendship> friendships = friendshipService.findAllByFriendId(friendId);
        return new ResponseEntity<>(friendships, HttpStatus.OK);
    }

    @GetMapping("/specific/{userId}/{friendId}")
    public ResponseEntity<List<Friendship>> findSpecificFriendship(@PathVariable("userId") Integer userId,
                                                                   @PathVariable("friendId") Integer friendId) {
        List<Friendship> friendships = friendshipService.retrieveSpecificFriendship(userId, friendId);
        return new ResponseEntity<>(friendships, HttpStatus.OK);
    }

    @GetMapping("/friends/are/{userId1}/{userId2}")
    public ResponseEntity<Boolean> areUsersInAFriendStatus(@PathVariable("userId1") Integer userId1,
                                                           @PathVariable("userId2") Integer userId2) {
        Boolean status = friendshipService.areTwoUsersFriends(userId1, userId2);
        return new ResponseEntity<>(status, HttpStatus.OK);
    }

    @GetMapping("friends/notification/count/{userId}")
    public ResponseEntity<Integer> countFriendshipNotifications(@PathVariable("userId") Integer userId) {
        Integer count = friendshipService.countNotificationsForAnUser(userId);
        return new ResponseEntity<>(count, HttpStatus.OK);
    }

    @GetMapping("friends/active/friendships/{userId}")
    public ResponseEntity<List<Friendship>> getActiveFriendships(@PathVariable("userId") Integer userId) {
        List<Friendship> friendships = friendshipService.getFriendshipsThatRequireNotificationAction(userId);
        return new ResponseEntity<>(friendships, HttpStatus.OK);
    }

    @GetMapping("friends/active/pending/friendships/{userId}")
    public ResponseEntity<List<Friendship>> getActivePendingFriendships(@PathVariable("userId") Integer userId) {
        List<Friendship> friendships = friendshipService.getPendingFriendshipRequests(userId);
        return new ResponseEntity<>(friendships, HttpStatus.OK);
    }

    @PutMapping("friends/set/notified/{userId}/{friendId}")
    public ResponseEntity<Map<String, Boolean>> setStatusToNotified(@PathVariable("userId") Integer userId,
                                                                    @PathVariable("friendId") Integer friendId) {
        friendshipService.setStatusToNotified(userId, friendId);
        Map<String, Boolean> map = new HashMap<>();
        map.put("success", true);
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @PutMapping("friends/change/friendship/request/{userId}/{friendId}")
    public ResponseEntity<Map<String, Boolean>> changeFriendshipRequest(@RequestBody Map<String, Object> friendshipMap,
                                                                        @PathVariable("userId") Integer userId,
                                                                        @PathVariable("friendId") Integer friendId) {
        String notificationState = (String) friendshipMap.get("notificationState");
        friendshipService.changeFriendRequestStatus(userId, friendId, notificationState);
        Map<String, Boolean> map = new HashMap<>();
        map.put("success", true);
        return new ResponseEntity<>(map, HttpStatus.OK);
    }
}
