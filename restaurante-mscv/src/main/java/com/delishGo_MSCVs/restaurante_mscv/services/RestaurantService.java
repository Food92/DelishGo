package com.delishGo_MSCVs.restaurante_mscv.services;

import com.delishGo_MSCVs.restaurante_mscv.models.Restaurant;

import java.util.List;

public interface RestaurantService {
    List<Restaurant> findAll();
    Restaurant findById(Long idRestaurant);
    Restaurant save(Restaurant restaurant);
    void delete(Long idRestaurant);
    Restaurant update(Long idRestaurant, Restaurant restaurant);
}
