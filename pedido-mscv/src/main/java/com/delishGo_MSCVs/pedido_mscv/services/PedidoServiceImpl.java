package com.delishGo_MSCVs.pedido_mscv.services;

import com.delishGo_MSCVs.cliente_mscv.exception.ClienteException;
import com.delishGo_MSCVs.pedido_mscv.client.ClienteClient;
import com.delishGo_MSCVs.pedido_mscv.client.DetallePedidoClient;
import com.delishGo_MSCVs.pedido_mscv.client.ProductoClient;
import com.delishGo_MSCVs.pedido_mscv.client.RestaurantClient;
import com.delishGo_MSCVs.pedido_mscv.exception.PedidoException;
import com.delishGo_MSCVs.pedido_mscv.models.Pedido;
import com.delishGo_MSCVs.pedido_mscv.models.dtos.*;
import com.delishGo_MSCVs.pedido_mscv.repositories.PedidoRepository;
import com.delishGo_MSCVs.restaurante_mscv.exception.RestaurantException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

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
    public List<PedidoResponseDTO> findAll() {
        return pedidoRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public PedidoResponseDTO findById(Long id) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new PedidoException("Pedido con ID:" + id + " no encontrado"));
        return mapToResponse(pedido);
    }

    @Override
    public PedidoResponseDTO save(PedidoDTO pedidoDTO) {
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

        // Validar productos y asignar precio unitario
        pedidoDTO.getDetallesPedido().forEach(detalle -> {
            ProductoDTO producto = productoClient.getProductoById(detalle.getIdProducto());
            if (producto == null) {
                throw new PedidoException("Producto no encontrado con ID: " + detalle.getIdProducto());
            }
            detalle.setPrecioUnitario(producto.getPrecio());
        });

        // Calcular monto total
        double total = pedidoDTO.getDetallesPedido().stream()
                .mapToDouble(DetallePedidoDTO::getSubtotal)
                .sum();

        // Guardar pedido
        Pedido pedido = new Pedido();
        pedido.setIdCliente(clienteDTO.getIdCliente());
        pedido.setIdRestaurant(restaurantDTO.getIdRestaurant());
        pedido.setMontoTotal(total);
        pedido.setEstado(pedidoDTO.getEstado());
        pedido.setHoraPedido(LocalDateTime.now());

        Pedido savedPedido = pedidoRepository.save(pedido);

        // Respuesta (solo devuelve lo que vino en el DTO)
        PedidoResponseDTO response = mapToResponse(savedPedido);
        response.setDetallesPedido(pedidoDTO.getDetallesPedido());
        response.setMontoTotal(total);
        return response;
    }

    @Override
    public PedidoResponseDTO update(Long id, PedidoDTO pedidoDTO) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new PedidoException("Pedido con ID:" + id + " no encontrado"));

        // Actualizar estado
        pedido.setEstado(pedidoDTO.getEstado());

        // Validar productos y recalcular
        pedidoDTO.getDetallesPedido().forEach(detalle -> {
            ProductoDTO producto = productoClient.getProductoById(detalle.getIdProducto());
            if (producto == null) {
                throw new PedidoException("Producto no encontrado con ID: " + detalle.getIdProducto());
            }
            detalle.setPrecioUnitario(producto.getPrecio());
        });

        double total = pedidoDTO.getDetallesPedido().stream()
                .mapToDouble(DetallePedidoDTO::getSubtotal)
                .sum();

        pedido.setMontoTotal(total);
        pedido.setHoraPedido(LocalDateTime.now());
        Pedido updatedPedido = pedidoRepository.save(pedido);

        PedidoResponseDTO response = mapToResponse(updatedPedido);
        response.setDetallesPedido(pedidoDTO.getDetallesPedido());
        response.setMontoTotal(total);
        return response;
    }

    @Override
    public void deleteById(Long id) {
        if (!pedidoRepository.existsById(id)) {
            throw new PedidoException("Pedido con ID:" + id + " no encontrado");
        }
        pedidoRepository.deleteById(id);
    }

    @Override
    public List<PedidoResponseDTO> findByIdCliente(Long idCliente) {
        return pedidoRepository.findByIdCliente(idCliente).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<PedidoResponseDTO> findByIdRestaurant(Long idRestaurant) {
        return pedidoRepository.findByIdRestaurant(idRestaurant).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<PedidoResponseDTO> findByEstado(String estado) {
        return pedidoRepository.findByEstado(estado).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // 🔧 Método privado para mapear entidad → DTO
    private PedidoResponseDTO mapToResponse(Pedido pedido) {
        PedidoResponseDTO response = new PedidoResponseDTO();
        response.setIdPedido(pedido.getIdPedido());
        response.setEstado(pedido.getEstado());
        response.setHoraPedido(pedido.getHoraPedido());
        response.setIdCliente(pedido.getIdCliente());
        response.setIdRestaurant(pedido.getIdRestaurant());
        response.setMontoTotal(pedido.getMontoTotal());

        // ⚠️ Antes solo devolvías los detalles del DTO, no llamabas al microservicio
        response.setDetallesPedido(Collections.emptyList());

        return response;
    }
}