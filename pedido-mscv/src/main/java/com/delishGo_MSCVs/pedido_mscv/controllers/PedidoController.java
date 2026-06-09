package com.delishGo_MSCVs.pedido_mscv.controllers;

import com.delishGo_MSCVs.pedido_mscv.models.Pedido;
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

    // Obtener todos los pedidos
    @GetMapping
    public ResponseEntity<List<PedidoResponseDTO>> getAllPedidos() {
        return ResponseEntity.ok(pedidoService.findAll());
    }

    // Obtener pedido por ID
    @GetMapping("/{id}")
    public ResponseEntity<PedidoResponseDTO> findById(@PathVariable Long id) {
        PedidoResponseDTO response = pedidoService.findById(id);
        return ResponseEntity.ok(response);
    }

    // Crear pedido
    @PostMapping
    public ResponseEntity<PedidoResponseDTO> save(@Valid @RequestBody PedidoDTO pedidoDTO) {
        PedidoResponseDTO response = pedidoService.save(pedidoDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // Actualizar pedido
    @PutMapping("/{id}")
    public ResponseEntity<PedidoResponseDTO> updatePedido(@PathVariable Long id,
                                                          @RequestBody PedidoDTO pedidoDTO) {
        PedidoResponseDTO pedidoActualizado = pedidoService.update(id, pedidoDTO);
        return ResponseEntity.ok(pedidoActualizado);
    }

    // Eliminar pedido
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deletePedido(@PathVariable Long id) {
        pedidoService.deleteById(id);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Pedido con ID " + id + " eliminado correctamente");
        return ResponseEntity.ok(response);
    }

    // 🔎 Buscar pedidos por cliente
    @GetMapping("/cliente/{idCliente}")
    public ResponseEntity<List<PedidoResponseDTO>> getByCliente(@PathVariable Long idCliente) {
        return ResponseEntity.ok(pedidoService.findByIdCliente(idCliente));
    }

    // 🔎 Buscar pedidos por restaurante
    @GetMapping("/restaurante/{idRestaurant}")
    public ResponseEntity<List<PedidoResponseDTO>> getByRestaurante(@PathVariable Long idRestaurant) {
        return ResponseEntity.ok(pedidoService.findByIdRestaurant(idRestaurant));
    }

    // 🔎 Buscar pedidos por estado
    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<PedidoResponseDTO>> getByEstado(@PathVariable String estado) {
        return ResponseEntity.ok(pedidoService.findByEstado(estado));
    }


}
