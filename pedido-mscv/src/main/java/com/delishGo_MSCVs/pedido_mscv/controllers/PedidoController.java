package com.delishGo_MSCVs.pedido_mscv.controllers;

import com.delishGo_MSCVs.pedido_mscv.models.dtos.PedidoDTO;
import com.delishGo_MSCVs.pedido_mscv.models.dtos.PedidoResponseDTO;
import com.delishGo_MSCVs.pedido_mscv.services.PedidoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/pedidos")
@Validated
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    // 🔎 OBTENER TODOS LOS PEDIDOS (Usa: listar-pedidos-lambda)
    @GetMapping
    public ResponseEntity<List<PedidoResponseDTO>> getAllPedidos() {
        return ResponseEntity.ok(pedidoService.findAll());
    }

    // 🔄 PROCESAR PEDIDO DESDE SQS (Usa: procesar-pedido-lambda)
    @PostMapping("/procesar")
    public ResponseEntity<?> procesarPedido() {
        // 1. Llama a tu servicio que extrae y cambia el estado a PROCESADO en H2
        var pedidoProcesado = pedidoService.procesarSiguientePedido();

        // 2. Creamos el mapa para estructurar la respuesta idéntica a tu ejemplo
        Map<String, String> respuesta = new HashMap<>();

        if (pedidoProcesado != null) {
            // Si había un mensaje en SQS y se procesó con éxito:
            respuesta.put("message", "Solicitud recibida");
            respuesta.put("status", "Pedido ID " + pedidoProcesado.getIdPedido() + " cambiado a PROCESADO");
            return ResponseEntity.ok(respuesta); // Esto envía el HTTP 200 con tu JSON customizado
        }

        // Si entraste al endpoint pero SQS estaba vacío:
        respuesta.put("message", "No hay pedidos en cola");
        return ResponseEntity.ok(respuesta);
    }


    @GetMapping("/{id}")
    public ResponseEntity<PedidoResponseDTO> findById(@PathVariable Long id) {
        PedidoResponseDTO response = pedidoService.findById(id);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<PedidoResponseDTO> save(@Valid @RequestBody PedidoDTO pedidoDTO) {
        PedidoResponseDTO response = pedidoService.save(pedidoDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PedidoResponseDTO> updatePedido(@PathVariable Long id,
                                                          @RequestBody PedidoDTO pedidoDTO) {
        PedidoResponseDTO pedidoActualizado = pedidoService.update(id, pedidoDTO);
        return ResponseEntity.ok(pedidoActualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deletePedido(@PathVariable Long id) {
        pedidoService.deleteById(id);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Pedido con ID " + id + " eliminado correctamente");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/cliente/{idCliente}")
    public ResponseEntity<List<PedidoResponseDTO>> getByCliente(@PathVariable Long idCliente) {
        return ResponseEntity.ok(pedidoService.findByIdCliente(idCliente));
    }

    @GetMapping("/restaurante/{idRestaurant}")
    public ResponseEntity<List<PedidoResponseDTO>> getByRestaurante(@PathVariable Long idRestaurant) {
        return ResponseEntity.ok(pedidoService.findByIdRestaurant(idRestaurant));
    }

    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<PedidoResponseDTO>> getByEstado(@PathVariable String estado) {
        return ResponseEntity.ok(pedidoService.findByEstado(estado));
    }
}