package com.example.user.service.UserService.controllers;

import com.example.user.service.UserService.entities.User;
import com.example.user.service.UserService.services.UserService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    private Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @PostMapping()
    public ResponseEntity<User> createUser(@RequestBody User user){
        User user1 = userService.saveUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(user1);
    }
    @GetMapping("/{userId}")
    @CircuitBreaker(name = "ratingHotelBreaker", fallbackMethod = "ratingHotelFallback")
    public ResponseEntity<User> getSingleUser(@PathVariable String userId){
         User user = userService.getUser(userId);
         return ResponseEntity.ok(user);
    }

    public ResponseEntity<User> ratingHotelFallback(String userId, Exception exception){
        LOGGER.info("Fallback is executed because service is down.", exception.getMessage());
        User user = User.builder()
                .userId("1234")
                .name("temporary")
                .email("temporary@gamail.com")
                .about("Service is down so that temporary user has been passed.")
                .build();

        return new ResponseEntity<>(user, HttpStatus.OK);
    }
    @GetMapping()
    public ResponseEntity<List<User>> getAllUser(){
        List<User> list = userService.getAllUsers();
        return ResponseEntity.ok(list);
    }
}
