package com.social.app.resources;

import com.social.app.domain.Friendship;
import com.social.app.services.FriendshipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
}
