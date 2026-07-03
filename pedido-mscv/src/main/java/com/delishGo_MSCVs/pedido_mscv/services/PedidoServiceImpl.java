package com.delishGo_MSCVs.pedido_mscv.services;

import com.delishGo_MSCVs.pedido_mscv.client.ClienteClient;
import com.delishGo_MSCVs.pedido_mscv.client.ProductoClient;
import com.delishGo_MSCVs.pedido_mscv.client.RestaurantClient;
import com.delishGo_MSCVs.pedido_mscv.exception.PedidoException; // Usamos solo excepciones locales
import com.delishGo_MSCVs.pedido_mscv.models.Pedido;
import com.delishGo_MSCVs.pedido_mscv.models.dtos.*;
import com.delishGo_MSCVs.pedido_mscv.repositories.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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
                .orElseThrow(() -> new PedidoException("Pedido con ID: " + id + " no encontrado"));
        return mapToResponse(pedido);
    }

    @Override
    public PedidoResponseDTO save(PedidoDTO pedidoDTO) {
        // 1. Validar cliente (Lanza excepción local si falla)
        ClienteDTO clienteDTO = clienteClient.getClienteById(pedidoDTO.getIdCliente());
        if (clienteDTO == null) {
            throw new PedidoException("No se puede crear el pedido: Cliente no encontrado con ID: " + pedidoDTO.getIdCliente());
        }

        // 2. Validar restaurante
        RestaurantDTO restaurantDTO = restaurantClient.getRestaurantById(pedidoDTO.getIdRestaurant());
        if (restaurantDTO == null) {
            throw new PedidoException("No se puede crear el pedido: Restaurante no encontrado con ID: " + pedidoDTO.getIdRestaurant());
        }

        // 3. Validar detalles vacíos
        if (pedidoDTO.getDetallesPedido() == null || pedidoDTO.getDetallesPedido().isEmpty()) {
            throw new PedidoException("El pedido debe tener al menos un producto en el detalle");
        }

        // 4. Validar productos y calcular subtotales y totales de manera segura en el Backend
        double totalPedido = 0.0;
        for (DetallePedidoDTO detalle : pedidoDTO.getDetallesPedido()) {
            ProductoDTO producto = productoClient.getProductoById(detalle.getIdProducto());
            if (producto == null) {
                throw new PedidoException("Producto no encontrado con ID: " + detalle.getIdProducto());
            }

            // Forzar los valores reales del catálogo de productos
            detalle.setPrecioUnitario(producto.getPrecio());
            double subtotal = producto.getPrecio() * detalle.getCantidad();
            detalle.setSubtotal(subtotal);

            totalPedido += subtotal;
        }

        // 5. Guardar entidad Pedido
        Pedido pedido = new Pedido();
        pedido.setIdCliente(clienteDTO.getIdCliente());
        pedido.setIdRestaurant(restaurantDTO.getIdRestaurant());
        pedido.setMontoTotal(totalPedido);
        pedido.setEstado(pedidoDTO.getEstado());
        pedido.setHoraPedido(LocalDateTime.now());

        Pedido savedPedido = pedidoRepository.save(pedido);

        // 6. Mapear respuesta final incluyendo los detalles procesados
        PedidoResponseDTO response = mapToResponse(savedPedido);
        response.setDetallesPedido(pedidoDTO.getDetallesPedido());
        return response;
    }

    @Override
    public PedidoResponseDTO update(Long id, PedidoDTO pedidoDTO) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new PedidoException("Pedido con ID: " + id + " no encontrado"));

        pedido.setEstado(pedidoDTO.getEstado());

        if (pedidoDTO.getDetallesPedido() != null && !pedidoDTO.getDetallesPedido().isEmpty()) {
            double totalPedido = 0.0;
            for (DetallePedidoDTO detalle : pedidoDTO.getDetallesPedido()) {
                ProductoDTO producto = productoClient.getProductoById(detalle.getIdProducto());
                if (producto == null) {
                    throw new PedidoException("Producto no encontrado con ID: " + detalle.getIdProducto());
                }
                detalle.setPrecioUnitario(producto.getPrecio());
                double subtotal = producto.getPrecio() * detalle.getCantidad();
                detalle.setSubtotal(subtotal);
                totalPedido += subtotal;
            }
            pedido.setMontoTotal(totalPedido);
        }

        pedido.setHoraPedido(LocalDateTime.now());
        Pedido updatedPedido = pedidoRepository.save(pedido);

        PedidoResponseDTO response = mapToResponse(updatedPedido);
        response.setDetallesPedido(pedidoDTO.getDetallesPedido());
        return response;
    }

    @Override
    public void deleteById(Long id) {
        if (!pedidoRepository.existsById(id)) {
            throw new PedidoException("Pedido con ID: " + id + " no encontrado");
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

    private PedidoResponseDTO mapToResponse(Pedido pedido) {
        PedidoResponseDTO response = new PedidoResponseDTO();
        response.setIdPedido(pedido.getIdPedido());
        response.setEstado(pedido.getEstado());
        response.setHoraPedido(pedido.getHoraPedido());
        response.setIdCliente(pedido.getIdCliente());
        response.setIdRestaurant(pedido.getIdRestaurant());
        response.setMontoTotal(pedido.getMontoTotal());

        // NOTA: Si cuentas con un DetallePedidoClient o repositorio de detalles de pedido,
        // aquí deberías buscar los detalles asociados a 'pedido.getIdPedido()' e inyectarlos.
        // Por ahora, se inicializa vacío de manera segura para evitar NullPointerException.
        response.setDetallesPedido(new java.util.ArrayList<>());

        return response;
    }
}