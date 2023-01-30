package com.social.app.services;

import com.social.app.domain.User;
import com.social.app.exception.UaAuthException;
import com.social.app.exception.UaBadRequestException;
import com.social.app.exception.UaResourceNotFoundException;
import com.social.app.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @InjectMocks
    UserServiceImpl userService;

    @Mock
    UserRepository userRepository;

    private User user;

    @BeforeEach
    public void setUp() {
        user = new User();
        user.setUserId(1);
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setEmail("johndoe@example.com");
        user.setPassword("password");
        user.setMajor("Computer Science");
        user.setMinor("Mathematics");
    }

    @Test
    void validateUser_Success() throws UaAuthException {
        // Given
        String email = "user@email.com";
        String password = "password";
        User expectedUser = new User();
        expectedUser.setEmail(email);
        expectedUser.setPassword(password);
        when(userRepository.findByEmailAndPassword(email, password)).thenReturn(expectedUser);

        // When
        User actualUser = userService.validateUser(email, password);

        // Then
        assertEquals(expectedUser, actualUser);
    }

    @Test
    void validateUser_ThrowsException_WhenEmailNotFound() {
        // Given
        String email = "user@email.com";
        String password = "password";
        when(userRepository.findByEmailAndPassword(email, password))
                .thenThrow(new UaAuthException("Invalid email or password"));

        // When
        Exception exception = assertThrows(UaAuthException.class, () -> {
            userService.validateUser(email, password);
        });

        // Then
        assertEquals("Invalid email or password", exception.getMessage());
    }

    @Test
    public void registerUserSuccess() throws UaAuthException {
        String email = "johndoe@example.com";
        String password = "password";

        Mockito.when(userRepository.getCountByEmail(email)).thenReturn(0);
        Mockito.when(userRepository.create(user.getFirstName(), user.getLastName(), email, password,
                user.getMajor(), user.getMinor())).thenReturn(1);
        Mockito.when(userRepository.findById(user.getUserId())).thenReturn(user);

        User result = userService.registerUser(user.getFirstName(), user.getLastName(), email, password,
                user.getMajor(), user.getMinor());

        assertEquals(user.getUserId(), result.getUserId());
        assertEquals(user.getFirstName(), result.getFirstName());
        assertEquals(user.getLastName(), result.getLastName());
        assertEquals(user.getEmail(), result.getEmail());
        assertEquals(user.getPassword(), result.getPassword());
        assertEquals(user.getMajor(), result.getMajor());
        assertEquals(user.getMinor(), result.getMinor());
    }

    @Test()
    public void registerUserEmailExists() throws UaAuthException {
        String email = "johndoe@example.com";
        String password = "password";

        Mockito.when(userRepository.getCountByEmail(email)).thenReturn(1);

        Exception exception = assertThrows(UaAuthException.class, () -> {
            userService.registerUser(user.getFirstName(), user.getLastName(), email, password,
                    user.getMajor(), user.getMinor());
        });

        assertEquals("Email already in use!", exception.getMessage());
    }

    @Test()
    public void testRegisterUserInvalidEmail() throws UaAuthException {
        Exception exception = assertThrows(UaAuthException.class, () -> userService.registerUser(
                "John",
                "Doe",
                "invalid_email",
                "password",
                "Computer Science",
                "Artificial Intelligence"
        ));

        assertEquals("Invalid email format", exception.getMessage());
    }

    @Test
    public void testGetUserById() throws UaResourceNotFoundException {
        User user = new User();
        user.setUserId(1);
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setEmail("johndoe@gmail.com");
        user.setPassword("password");

        when(userRepository.findById(1)).thenReturn(user);

        User result = userService.getUserById(1);

        assertEquals(1, result.getUserId().intValue());
        assertEquals("John", result.getFirstName());
        assertEquals("Doe", result.getLastName());
        assertEquals("johndoe@gmail.com", result.getEmail());
        assertEquals("password", result.getPassword());

        verify(userRepository, times(1)).findById(1);
    }

    @Test()
    public void testGetUserByIdNotFound() throws UaResourceNotFoundException {
        when(userRepository.findById(1)).thenReturn(null);

        userService.getUserById(1);

        verify(userRepository, times(1)).findById(1);
    }

    @Test
    public void testUpdateUserProfilePicture() throws UaBadRequestException {
        userService.updateUserProfilePicture("profile_picture.jpg", 1);

        verify(userRepository, times(1)).updateProfilePicture("profile_picture.jpg", 1);
    }

    @Test
    public void testUpdateUserSettings() throws UaBadRequestException {
        userService.updateUserSettings(true, 1);

        verify(userRepository, times(1)).updateUserSettings(true, 1);
    }
}