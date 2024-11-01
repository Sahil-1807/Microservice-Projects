package com.example.hotel.service.services;

import com.example.hotel.service.entities.Hotel;

import java.util.List;

public interface HotelService {

    Hotel createHotel(Hotel hotel);

    List<Hotel> getAll();

    Hotel getHotel(String id);

}
