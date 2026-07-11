package com.delishGo_MSCVs.restaurante_mscv.services;

import com.delishGo_MSCVs.restaurante_mscv.exception.RestaurantException;
import com.delishGo_MSCVs.restaurante_mscv.models.Restaurant;
import com.delishGo_MSCVs.restaurante_mscv.repositories.RestaurantRespository;
// 👇 Importación de AWS SQS
import io.awspring.cloud.sqs.annotation.SqsListener;
// 👆
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RestaurantImpl implements RestaurantService {

    @Autowired
    private RestaurantRespository restaurantRespository;

    @Transactional(readOnly = true)
    @Override
    public List<Restaurant> findAll() {
        return restaurantRespository.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public Restaurant findById(Long idRestaurant) {
        return restaurantRespository.findById(idRestaurant).orElseThrow(
                ()->new RestaurantException("Restaurant con ID: "+idRestaurant + " no existe"));
    }

    @Transactional
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

    @Transactional
    @Override
    public void delete(Long idRestaurant) {
        if(!restaurantRespository.existsById(idRestaurant)){
            throw new RestaurantException("Restaurant con ID: "+idRestaurant+" no existe");
        }
        restaurantRespository.deleteById(idRestaurant);
    }

    @Transactional
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

    // 🚀 👇 ESCUCHAR MENSAJES DE SQS 👇 🚀
    // Recibimos el mensaje como String (JSON) para que no haya problemas de acoplamiento.
    @SqsListener("${AWS_SQS_QUEUE_URL}")
    public void recibirNotificacionNuevoPedido(String jsonPedidoEvent) {
        System.out.println("🔔 ¡ATENCIÓN RESTAURANTE! 🔔");
        System.out.println("Ha ingresado un nuevo pedido desde la cola SQS:");
        System.out.println(jsonPedidoEvent);

        // Aquí puedes agregar la lógica para notificar a un WebSocket del frontend,
        // guardarlo en una base de datos local de "tickets", etc.
    }
}