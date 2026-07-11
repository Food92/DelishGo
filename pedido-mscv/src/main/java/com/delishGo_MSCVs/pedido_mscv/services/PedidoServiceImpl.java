package com.delishGo_MSCVs.pedido_mscv.services;

import com.delishGo_MSCVs.pedido_mscv.client.ClienteClient;
import com.delishGo_MSCVs.pedido_mscv.client.ProductoClient;
import com.delishGo_MSCVs.pedido_mscv.client.RestaurantClient;
import com.delishGo_MSCVs.pedido_mscv.exception.PedidoException;
import com.delishGo_MSCVs.pedido_mscv.models.Pedido;
import com.delishGo_MSCVs.pedido_mscv.models.dtos.*;
import com.delishGo_MSCVs.pedido_mscv.repositories.PedidoRepository;

// 👇 Importaciones de AWS SQS
import io.awspring.cloud.sqs.operations.SqsTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PedidoServiceImpl implements PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired(required = false)
    private ClienteClient clienteClient;

    @Autowired(required = false)
    private RestaurantClient restaurantClient;

    @Autowired(required = false)
    private ProductoClient productoClient;

    @Autowired
    private SqsTemplate sqsTemplate;

    @Value("${AWS_SQS_QUEUE_URL}")
    private String queueUrl;

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
        if (pedidoDTO.getDetallesPedido() == null || pedidoDTO.getDetallesPedido().isEmpty()) {
            throw new PedidoException("El pedido debe tener al menos un detalle");
        }

        double total = pedidoDTO.getDetallesPedido().stream()
                .mapToDouble(DetallePedidoDTO::getSubtotal)
                .sum();

        Pedido pedido = new Pedido();
        pedido.setIdCliente(pedidoDTO.getIdCliente());
        pedido.setIdRestaurant(pedidoDTO.getIdRestaurant());
        pedido.setMontoTotal(total);
        pedido.setEstado(pedidoDTO.getEstado() != null ? pedidoDTO.getEstado() : "PENDIENTE");
        pedido.setHoraPedido(LocalDateTime.now());

        Pedido savedPedido = pedidoRepository.save(pedido);
        System.out.println("✅ Pedido guardado con éxito en H2 en memoria con ID: " + savedPedido.getIdPedido());

        PedidoResponseDTO response = mapToResponse(savedPedido);
        response.setDetallesPedido(pedidoDTO.getDetallesPedido());
        response.setMontoTotal(total);

        try {
            sqsTemplate.send(to -> to
                    .queue(queueUrl)
                    .payload(response)
            );
            System.out.println("🚀 Mensaje enviado a SQS con éxito para el pedido ID: " + savedPedido.getIdPedido());
        } catch (Exception e) {
            System.err.println("❌ Error al despachar el mensaje a la cola SQS: " + e.getMessage());
        }

        return response;
    }

    @Override
    public PedidoResponseDTO update(Long id, PedidoDTO pedidoDTO) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new PedidoException("Pedido con ID:" + id + " no encontrado"));

        pedido.setEstado(pedidoDTO.getEstado());

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

    // 🔄 LOGICA PARA PROCESAR EL SIGUIENTE MENSAJE DESDE SQS
    @Override
    public PedidoResponseDTO procesarSiguientePedido() {
        try {
            // Saca 1 mensaje de la cola convirtiéndolo a PedidoResponseDTO automáticamente
            var messageOptional = sqsTemplate.receive(from -> from.queue(queueUrl), PedidoResponseDTO.class);

            if (messageOptional.isPresent()) {
                PedidoResponseDTO pedidoSqs = messageOptional.get().getPayload();
                Long idPedido = pedidoSqs.getIdPedido();

                System.out.println("📥 Mensaje extraído de SQS para procesar el Pedido ID: " + idPedido);

                // Buscar en la base H2 para cambiar su estado
                Pedido pedidoEnDb = pedidoRepository.findById(idPedido)
                        .orElseThrow(() -> new PedidoException("El pedido con ID " + idPedido + " no existe en la BD H2"));

                pedidoEnDb.setEstado("PROCESADO");
                Pedido updatedPedido = pedidoRepository.save(pedidoEnDb);

                System.out.println("✅ Estado del pedido ID " + idPedido + " actualizado a PROCESADO en H2.");
                return mapToResponse(updatedPedido);
            } else {
                System.out.println("📋 No hay mensajes en la cola SQS pendientes.");
                return null;
            }
        } catch (Exception e) {
            System.err.println("❌ Error en el consumidor de SQS: " + e.getMessage());
            throw new PedidoException("Error procesando mensaje de SQS: " + e.getMessage());
        }
    }

    private PedidoResponseDTO mapToResponse(Pedido pedido) {
        PedidoResponseDTO response = new PedidoResponseDTO();
        response.setIdPedido(pedido.getIdPedido());
        response.setEstado(pedido.getEstado());
        response.setHoraPedido(pedido.getHoraPedido());
        response.setIdCliente(pedido.getIdCliente());
        response.setIdRestaurant(pedido.getIdRestaurant());
        response.setMontoTotal(pedido.getMontoTotal());
        response.setDetallesPedido(Collections.emptyList());
        return response;
    }
}