package com.social.app.resources;

import com.social.app.Constants;
import com.social.app.domain.User;
import com.social.app.model.ProfilePicture;
import com.social.app.model.UserSettings;
import com.social.app.services.UserService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserResource {

    @Autowired
    UserService userService;

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> loginUser(@RequestBody Map<String, Object> userMap) {
        String email = (String) userMap.get("email");
        String password = (String) userMap.get("password");
        User user = userService.validateUser(email, password);
        return new ResponseEntity<>(generateJWTToken(user), HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> registerUser(@RequestBody Map<String, Object> userMap) {
        String firstName = (String) userMap.get("firstName");
        String lastName = (String) userMap.get("lastName");
        String email = (String) userMap.get("email");
        String password = (String) userMap.get("password");
        String major = (String) userMap.get("major");
        String minor = (String) userMap.get("minor");
        User user = userService.registerUser(firstName, lastName, email, password, major, minor);
        return new ResponseEntity<>(generateJWTToken(user), HttpStatus.OK);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable("userId") Integer userId) {
        User user = userService.getUserById(userId);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PutMapping("/profile-picture/{userId}")
    public ResponseEntity<Map<String, Boolean>> updateProfilePicture(@PathVariable("userId") Integer userId,
                                                                     @RequestBody ProfilePicture profilePicture) {
        userService.updateUserProfilePicture(profilePicture.getBase64Image(), userId);
        Map<String, Boolean> map = new HashMap<>();
        map.put("success", true);
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @PutMapping("/settings/{userId}")
    public ResponseEntity<Map<String, Boolean>> updateUserSettings(@PathVariable("userId") Integer userId,
                                                                     @RequestBody UserSettings userSettings) {
        userService.updateUserSettings(userSettings.getNotifications(), userId);
        Map<String, Boolean> map = new HashMap<>();
        map.put("success", true);
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @PutMapping("/add-friend/{userId}/{friendId}")
    public ResponseEntity<Map<String, Boolean>> addFriend(@PathVariable("userId") Integer userId,
                                                          @PathVariable("friendId") Integer friendId) {
        userService.addFriend(userId, friendId);
        Map<String, Boolean> map = new HashMap<>();
        map.put("success", true);
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @PutMapping("/remove-friend/{userId}/{friendId}")
    public ResponseEntity<Map<String, Boolean>> removeFriend(@PathVariable("userId") Integer userId,
                                                          @PathVariable("friendId") Integer friendId) {
        userService.removeFriend(userId, friendId);
        Map<String, Boolean> map = new HashMap<>();
        map.put("success", true);
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    Map<String, String> generateJWTToken(User user) {
        long timestamp = System.currentTimeMillis();
        String token = Jwts.builder().signWith(SignatureAlgorithm.HS256, Constants.API_SECRET_KEY)
                .setIssuedAt(new Date(timestamp))
                .setExpiration(new Date(timestamp + Constants.TOKEN_VALIDITY))
                .claim("userId", user.getUserId())
                .claim("email", user.getEmail())
                .claim("firstName", user.getFirstName())
                .claim("lastName", user.getLastName())
                .compact();
        Map<String, String> map = new HashMap<>();
        map.put("token", token);
        return map;
    }
}