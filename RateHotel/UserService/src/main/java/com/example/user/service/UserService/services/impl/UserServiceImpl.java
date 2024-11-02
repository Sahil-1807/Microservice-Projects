package com.example.user.service.UserService.services.impl;

import com.example.user.service.UserService.entities.Hotel;
import com.example.user.service.UserService.entities.Rating;
import com.example.user.service.UserService.entities.User;
import com.example.user.service.UserService.exceptions.ResourceNotFoundException;
import com.example.user.service.UserService.externalservices.HotelService;
import com.example.user.service.UserService.externalservices.RatingService;
import com.example.user.service.UserService.repository.UserRepository;
import com.example.user.service.UserService.services.UserService;
import org.antlr.v4.runtime.RecognitionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    private Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private HotelService hotelService;

    @Autowired
    private RatingService ratingService;

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
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User with given id is not found. id: " + userId));
        // uri = http://localhost:8083/ratings/get/by/user/user_id
        //Rating[] forObject = restTemplate.getForObject("http://localhost:8083/ratings/get/by/user/" + user.getUserId(), Rating[].class);
        //LOGGER.info("{}", forObject);

        List<Rating> ratings = ratingService.getRatings(userId);


        List<Rating> ratingList = ratings.stream().map(rating -> {
            // uri = http://localhost:8082/hotels/get/hotel_id
            //ResponseEntity<Hotel> forEntity = restTemplate.getForEntity("http://localhost:8082/hotels/get/" + rating.getHotelId(), Hotel.class);
            // here instead of using rest template and uri we are using Feing client and interface of other services.
            Hotel hotel = hotelService.getHotel(rating.getHotelId());

            rating.setHotel(hotel);
            return rating;
        }).collect(Collectors.toList());

        user.setRatings(ratingList);
        return user;
    }
}
