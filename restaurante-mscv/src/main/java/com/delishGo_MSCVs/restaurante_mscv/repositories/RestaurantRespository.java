package com.delishGo_MSCVs.restaurante_mscv.repositories;

import com.delishGo_MSCVs.restaurante_mscv.models.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RestaurantRespository extends JpaRepository<Restaurant, Long> {
    Optional<Restaurant> findById(long id);
    Optional<Restaurant> findByCorreo(String correo);
    boolean existsByCorreo(String correo);
}
