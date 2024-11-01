package com.example.rating.service.repositories;

import com.example.rating.service.entities.Rating;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RatingsRepository extends MongoRepository<Rating, String> {

    List<Rating> findUserByUserId(String userId);
    List<Rating> findUserByHotelId(String hotelId);
}
