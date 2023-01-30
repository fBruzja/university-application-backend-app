package com.social.app.resources;

import com.social.app.Constants;
import com.social.app.domain.User;
import com.social.app.model.ProfilePicture;
import com.social.app.model.UserSettings;
import com.social.app.services.UserService;
import io.jsonwebtoken.Jwts;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserResourceTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserResource userResource;

    private Integer userId = 123;
    private UserSettings userSettings;

    @Before
    public void setUp() {
        userSettings = new UserSettings();
        userSettings.setNotifications(true);
    }

    @Test
    public void loginUser_returnsOKResponse() {
        Map<String, Object> userMap = new HashMap<>();
        userMap.put("email", "email@email.com");
        userMap.put("password", "password");

        User user = new User();
        when(userService.validateUser(any(), any())).thenReturn(user);

        ResponseEntity<Map<String, String>> response = userResource.loginUser(userMap);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void registerUser_returnsOKResponse() {
        Map<String, Object> userMap = new HashMap<>();
        userMap.put("firstName", "First");
        userMap.put("lastName", "Last");
        userMap.put("email", "email@email.com");
        userMap.put("password", "password");
        userMap.put("major", "major");
        userMap.put("minor", "minor");

        User user = new User();
        when(userService.registerUser(any(), any(), any(), any(), any(), any())).thenReturn(user);

        ResponseEntity<Map<String, String>> response = userResource.registerUser(userMap);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void getUserById_returnsOKResponse() {
        User user = new User();
        when(userService.getUserById(anyInt())).thenReturn(user);

        ResponseEntity<User> response = userResource.getUserById(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void updateProfilePicture_returnsOKResponse() {
        ProfilePicture profilePicture = new ProfilePicture();
        profilePicture.setBase64Image("image");

        ResponseEntity<Map<String, Boolean>> response = userResource.updateProfilePicture(1, profilePicture);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void updateUserSettings_success() {
        ResponseEntity<Map<String, Boolean>> response = userResource.updateUserSettings(userId, userSettings);

        assertEquals(HttpStatus.OK, response.getStatusCode());

        Map<String, Boolean> responseBody = response.getBody();
        assertEquals(true, responseBody.get("success"));
    }

    @Test
    public void generateJWTToken_success() {
        User user = new User();
        user.setUserId(userId);
        user.setEmail("test@example.com");
        user.setFirstName("Test");
        user.setLastName("User");

        Map<String, String> tokenMap = userResource.generateJWTToken(user);
        String token = tokenMap.get("token");

        Map<String, Object> claims = Jwts.parser().setSigningKey(Constants.API_SECRET_KEY).parseClaimsJws(token).getBody();

        assertEquals(userId, claims.get("userId"));
        assertEquals("test@example.com", claims.get("email"));
        assertEquals("Test", claims.get("firstName"));
        assertEquals("User", claims.get("lastName"));
    }
}
