package com.com.trading.PrachinX.service;

import com.com.trading.PrachinX.entity.User;
import com.com.trading.PrachinX.exception.CustomException;
import com.com.trading.PrachinX.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new CustomException("User not found", HttpStatus.NOT_FOUND));
    }

    public User getUserById(String id) {
        return userRepository.findById(id)
                .orElseThrow(() ->
                        new CustomException("User not found", HttpStatus.NOT_FOUND));
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User updateUser(Long id, User updatedUser) {
        User user = getUserById(String.valueOf(id));
        user.setFirstName(updatedUser.getFirstName());
        user.setLastName(updatedUser.getLastName());
        user.setPhoneNumber(updatedUser.getPhoneNumber());
        user.setProfileImage(updatedUser.getProfileImage());
        return userRepository.save(user);
    }

    public void deleteUser(Long id) {
        User user = getUserById(String.valueOf(id));
        userRepository.delete(user);
    }
}