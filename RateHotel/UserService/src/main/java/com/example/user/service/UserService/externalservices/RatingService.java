package com.example.user.service.UserService.externalservices;

import com.example.user.service.UserService.entities.Rating;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Service
@FeignClient(name = "RATINGSERVICE")
public interface RatingService {

    @GetMapping("ratings/user/{userId}")
    List<Rating> getRatings(@PathVariable String userId);
}
