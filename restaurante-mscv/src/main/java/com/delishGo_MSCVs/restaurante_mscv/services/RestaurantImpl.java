package com.delishGo_MSCVs.restaurante_mscv.services;


import com.delishGo_MSCVs.restaurante_mscv.exception.RestaurantException;
import com.delishGo_MSCVs.restaurante_mscv.models.Restaurant;
import com.delishGo_MSCVs.restaurante_mscv.repositories.RestaurantRespository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RestaurantImpl implements RestaurantService {
    @Autowired
    private RestaurantRespository restaurantRespository;


    @Override
    public List<Restaurant> findAll() {
        return this.restaurantRespository.findAll();
    }

    @Override
    public Restaurant findById(Long idRestaurant) {
        return this.restaurantRespository.findById(idRestaurant).orElseThrow(
                ()-> new RestaurantException("Restaurant con ID:" + idRestaurant + " no existe"));
    }

    @Override
    public Restaurant save(Restaurant restaurant) {
        // Si el ID viene nulo, es un nuevo registro
        if (restaurant.getId_restaurant() == null) {
            return restaurantRespository.save(restaurant);
        }

        // Si el ID viene con valor, validamos que no exista duplicado
        if (restaurantRespository.findById(restaurant.getId_restaurant()).isPresent()) {
            throw new RestaurantException("Ya existe un restaurante con ID: " + restaurant.getId_restaurant());
        }

        return restaurantRespository.save(restaurant);
    }


    @Override
    public void delete(Long idRestaurant) {
        if (idRestaurant == null) {
            throw new IllegalArgumentException("El ID del restaurante no puede ser nulo");
        }
        restaurantRespository.deleteById(idRestaurant);
    }


    @Override
    public Restaurant update(Long idRestaurant, Restaurant restaurant) {
        return this.restaurantRespository.findById(idRestaurant).map(element -> {
            element.setNombreRestaurant(restaurant.getNombreRestaurant());
            element.setDireccion(restaurant.getDireccion());
            element.setCategoria(restaurant.getCategoria());
            element.setCorreo(restaurant.getCorreo());
            return this.restaurantRespository.save(element);
        }).orElseThrow(
                ()-> new RestaurantException("Restaurant con ID:" + idRestaurant + " no existe")
        );
    }
}
