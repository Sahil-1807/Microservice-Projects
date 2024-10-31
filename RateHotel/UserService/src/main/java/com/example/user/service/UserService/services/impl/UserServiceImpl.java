package com.example.user.service.UserService.services.impl;

import com.example.user.service.UserService.entities.User;
import com.example.user.service.UserService.exceptions.ResourceNotFoundException;
import com.example.user.service.UserService.repository.UserRepository;
import com.example.user.service.UserService.services.UserService;
import org.antlr.v4.runtime.RecognitionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public User saveUser(User user) {
        String randomId = UUID.randomUUID().toString();
        user.setUserId(randomId);
        return userRepository.save(user);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUser(String userId) {
        return userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User with given id is not found. id: " + userId));
    }
}
