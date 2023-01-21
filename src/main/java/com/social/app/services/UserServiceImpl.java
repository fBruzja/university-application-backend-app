package com.social.app.services;

import com.social.app.domain.User;
import com.social.app.exception.UaAuthException;
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
        if(email != null) email = email.toLowerCase();
        return userRepository.findByEmailAndPassword(email, password);
    }

    @Override
    public User registerUser(String firstName, String lastName, String email, String password) throws UaAuthException {
        Pattern pattern = Pattern.compile("^(.+)@(.+)$"); // simple email pattern

        if(email != null) {
            email = email.toLowerCase();
        }

        if(!pattern.matcher(email).matches()) {
            throw new UaAuthException("Invalid email format");
        }

        Integer count = userRepository.getCountByEmail(email);
        if(count > 0) {
            throw new UaAuthException("Email already in use!");
        }

        Integer userId = userRepository.create(firstName, lastName, email, password);
        return userRepository.findById(userId);
    }
}
