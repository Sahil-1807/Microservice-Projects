package com.example.user.service.UserService.externalservices;

import com.example.user.service.UserService.entities.Rating;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "RATINGSERVICE")
public interface RatingService {

    @GetMapping("ratings/get/by/user/{userId}")
    List<Rating> getRatings(@PathVariable String userId);
}
