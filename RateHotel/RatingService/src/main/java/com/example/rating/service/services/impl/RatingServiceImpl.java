package com.example.rating.service.services.impl;

import com.example.rating.service.entities.Rating;
import com.example.rating.service.repositories.RatingsRepository;
import com.example.rating.service.services.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RatingServiceImpl implements RatingService {

    @Autowired
    private RatingsRepository ratingsRepository;
    @Override
    public Rating createRating(Rating rating) {
        return ratingsRepository.save(rating);
    }

    @Override
    public List<Rating> getRatings() {
        return ratingsRepository.findAll();
    }

    @Override
    public List<Rating> getRatingByUserId(String userId) {
        return ratingsRepository.findUserByUserId(userId);
    }

    @Override
    public List<Rating> getRatingByHotelId(String hotelId) {
        return ratingsRepository.findUserByHotelId(hotelId);
    }
}
