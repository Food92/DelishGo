package com.delishGo_MSCVs.pedido_mscv.services;

import com.delishGo_MSCVs.cliente_mscv.exception.ClienteException;
import com.delishGo_MSCVs.pedido_mscv.client.ClienteClient;
import com.delishGo_MSCVs.pedido_mscv.client.ProductoClient;
import com.delishGo_MSCVs.pedido_mscv.client.RestaurantClient;
import com.delishGo_MSCVs.pedido_mscv.exception.PedidoException;
import com.delishGo_MSCVs.pedido_mscv.models.DetallePedido;
import com.delishGo_MSCVs.pedido_mscv.models.Pedido;
import com.delishGo_MSCVs.pedido_mscv.models.dtos.*;
import com.delishGo_MSCVs.pedido_mscv.repositories.PedidoRepository;
import com.delishGo_MSCVs.restaurante_mscv.exception.RestaurantException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class PedidoServiceImpl implements PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private ClienteClient clienteClient;

    @Autowired
    private RestaurantClient restaurantClient;

    @Autowired
    private ProductoClient productoClient;

    @Override
    public List<Pedido> findAll() {
        return pedidoRepository.findAll();
    }

    @Override
    public Pedido findById(Long id) {
        return pedidoRepository.findById(id).orElseThrow(
                ()-> new PedidoException("Pedido con ID:" + id + "no encontrado")
        );
    }
    @Override
    public Pedido save(PedidoDTO pedidoDTO) {
        // Validar cliente
        ClienteDTO clienteDTO = clienteClient.getClienteById(pedidoDTO.getIdCliente());
        if (clienteDTO == null) {
            throw new ClienteException("Cliente no encontrado con ID: " + pedidoDTO.getIdCliente());
        }

        // Validar restaurante
        RestaurantDTO restaurantDTO = restaurantClient.getRestaurantById(pedidoDTO.getIdRestaurant());
        if (restaurantDTO == null) {
            throw new RestaurantException("Restaurante no encontrado con ID: " + pedidoDTO.getIdRestaurant());
        }

        // Validar detalles
        if (pedidoDTO.getDetallesPedido() == null || pedidoDTO.getDetallesPedido().isEmpty()) {
            throw new PedidoException("El pedido debe tener al menos un detalle");
        }

        // Validar producto y calcular subtotales
        pedidoDTO.getDetallesPedido().forEach(detalle -> {
            ProductoDTO producto = productoClient.getAllProductos()
                    .stream()
                    .filter(p -> p.getIdProducto().equals(detalle.getIdProducto()))
                    .findFirst()
                    .orElseThrow(() -> new PedidoException("Producto no encontrado con ID: " + detalle.getIdProducto()));

            detalle.setPrecioUnitario(producto.getPrecio());
            detalle.setSubtotal(detalle.getCantidad() * producto.getPrecio());
        });

        // Calcular monto total
        double total = pedidoDTO.getDetallesPedido().stream()
                .mapToDouble(DetallePedidoDTO::getSubtotal)
                .sum();
        pedidoDTO.setMontoTotal(total);

        // Convertir DTO a entidad
        Pedido pedido = new Pedido();
        pedido.setIdCliente(clienteDTO.getIdCliente());
        pedido.setIdRestaurant(restaurantDTO.getIdRestaurant());
        pedido.setPrecio(total); // aquí usas el total calculado
        pedido.setEstado(pedidoDTO.getEstado());
        pedido.setHoraPedido(LocalDateTime.now()); // fecha y hora actual

        return pedidoRepository.save(pedido);
    }



    @Override
    public Pedido update(PedidoDTO pedidoDTO, Long id) {
        return this.pedidoRepository.findById(id).map(p->{
            p.setEstado(pedidoDTO.getEstado());
            p.setPrecio(p.getPrecio());
            return  this.pedidoRepository.save(p);
        }).orElseThrow(()-> new PedidoException("Pedido con ID:" + id + "no encontrado"));
    }

    @Override
    public void deleteById(Long id) {
        if(!pedidoRepository.existsById(id)){
            throw new PedidoException("Pedido con ID:" + id + "no encontrado");
        }
        pedidoRepository.deleteById(id);

    }
}
