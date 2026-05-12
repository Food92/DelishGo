package com.delishGo_MSCVs.restaurante_mscv.services;


import com.delishGo_MSCVs.restaurante_mscv.exception.RestaurantException;
import com.delishGo_MSCVs.restaurante_mscv.models.Restaurant;
import com.delishGo_MSCVs.restaurante_mscv.repositories.RestaurantRespository;
import jakarta.persistence.EntityNotFoundException;
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
        return restaurantRespository.findAll();
    }

    @Override
    public Restaurant findById(Long idRestaurant) {
        return restaurantRespository.findById(idRestaurant).orElseThrow(
                ()->new RestaurantException("Restaurant con ID: "+idRestaurant + " no existe"));
    }

    @Override
    public Restaurant save(Restaurant restaurant) {
        if(restaurantRespository.findByCorreo(restaurant.getCorreo()).isPresent()){
            throw new RestaurantException("Restaurant con correo" +restaurant.getCorreo()+" ya existe");
        }
        if(restaurant.getIdRestaurant()!=null &&
                restaurantRespository.existsById(restaurant.getIdRestaurant())){
            throw new RestaurantException("Ya existe una restaurante con el ID: "+restaurant.getIdRestaurant());
        }
        return restaurantRespository.save(restaurant);

    }

    @Override
    public void delete(Long idRestaurant) {
        if(!restaurantRespository.existsById(idRestaurant)){
            throw new RestaurantException("Restaurant con ID: "+idRestaurant+" no existe");
        }
        restaurantRespository.deleteById(idRestaurant);

    }

    @Override
    public Restaurant update(Long idRestaurant, Restaurant restaurant) {
        Restaurant existente = restaurantRespository.findById(idRestaurant)
                .orElseThrow(() -> new EntityNotFoundException("Restaurante no encontrado con id: " + idRestaurant));

        existente.setNombreRestaurant(restaurant.getNombreRestaurant());
        existente.setCategoria(restaurant.getCategoria());
        existente.setDireccion(restaurant.getDireccion());
        existente.setCorreo(restaurant.getCorreo());

        return restaurantRespository.save(existente);
    }
}
