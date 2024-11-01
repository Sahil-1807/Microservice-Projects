package com.example.rating.service.services;

import com.example.rating.service.entities.Rating;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RatingService {

    Rating createRating(Rating rating);

    List<Rating> getRatings();
    List<Rating> getRatingByUserId(String userId);

    List<Rating> getRatingByHotelId(String hotelId);
}
