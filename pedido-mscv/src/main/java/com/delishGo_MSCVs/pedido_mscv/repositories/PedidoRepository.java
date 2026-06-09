package com.delishGo_MSCVs.pedido_mscv.repositories;

import com.delishGo_MSCVs.pedido_mscv.models.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {

    // Buscar pedidos por cliente
    List<Pedido> findByIdCliente(Long idCliente);

    // Buscar pedidos por restaurante
    List<Pedido> findByIdRestaurant(Long idRestaurant);

    // Buscar pedidos por estado (ej. "pendiente", "entregado")
    List<Pedido> findByEstado(String estado);

    // Buscar pedido por cliente y estado
    Optional<Pedido> findByIdClienteAndEstado(Long idCliente, String estado);

    // Buscar pedido por restaurante y estado
    List<Pedido> findByIdRestaurantAndEstado(Long idRestaurant, String estado);
}