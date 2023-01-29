package com.social.app.services;

import com.social.app.domain.User;
import com.social.app.exception.UaAuthException;
import com.social.app.exception.UaBadRequestException;
import com.social.app.exception.UaResourceNotFoundException;
import com.social.app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.regex.Pattern;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository; // using interface for decoupling against application tiers

    @Override
    public User validateUser(String email, String password) throws UaAuthException {
        if (email != null) email = email.toLowerCase();
        return userRepository.findByEmailAndPassword(email, password);
    }

    @Override
    public User registerUser(String firstName, String lastName, String email, String password, String major, String minor) throws UaAuthException {
        Pattern pattern = Pattern.compile("^(.+)@(.+)$"); // simple email pattern

        if (email != null) {
            email = email.toLowerCase();
        }

        if (!pattern.matcher(email).matches()) {
            throw new UaAuthException("Invalid email format");
        }

        Integer count = userRepository.getCountByEmail(email);
        if (count > 0) {
            throw new UaAuthException("Email already in use!");
        }

        Integer userId = userRepository.create(firstName, lastName, email, password, major, minor);
        return userRepository.findById(userId);
    }

    @Override
    public User getUserById(Integer userId) throws UaResourceNotFoundException {
        try {
            return userRepository.findById(userId);
        } catch (Exception e) {
            throw new UaResourceNotFoundException("User not found!");
        }
    }

    @Override
    public void updateUserProfilePicture(String profilePicture, Integer userId) throws UaBadRequestException {
        userRepository.updateProfilePicture(profilePicture, userId);
    }

}
