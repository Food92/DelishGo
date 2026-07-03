package com.delishGo_MSCVs.restaurante_mscv.controllers;

import com.delishGo_MSCVs.restaurante_mscv.models.Restaurant;
import com.delishGo_MSCVs.restaurante_mscv.services.RestaurantService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/restaurants")
@Validated

public class RestaurantController {
    @Autowired
    private RestaurantService restaurantService;

    @GetMapping
    public ResponseEntity<List<Restaurant>> findAll() {
        return ResponseEntity.status(HttpStatus.OK).body(this.restaurantService.findAll());
    }

    @GetMapping("/{idRestaurant}")
    public ResponseEntity<Restaurant> findById(@PathVariable Long idRestaurant) {
        return ResponseEntity.status(HttpStatus.OK).body(this.restaurantService.findById(idRestaurant));
    }

    @PostMapping
    public ResponseEntity<Restaurant> save(@Valid @RequestBody Restaurant restaurant) {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.restaurantService.save(restaurant));
    }

    @DeleteMapping("/{idRestaurant}")
    public ResponseEntity<Void> delete(@PathVariable Long idRestaurant) {
        restaurantService.delete(idRestaurant);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{idRestaurant}")
    public ResponseEntity<Restaurant> update(@PathVariable Long idRestaurant,
                                             @Valid @RequestBody Restaurant restaurant) {
        Restaurant updated = restaurantService.update(idRestaurant, restaurant);
        return ResponseEntity.ok(updated);
    }



}
