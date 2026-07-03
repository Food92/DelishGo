package com.delishGo_MSCVs.pedido_mscv.controllers;

import com.delishGo_MSCVs.pedido_mscv.models.dtos.DetallePedidoDTO;
import com.delishGo_MSCVs.pedido_mscv.services.DetallePedidoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/detallepedidos")
public class DetallePedidoController {

    @Autowired
    private DetallePedidoService service;

    // 🔎 Obtener detalles por pedido
    @GetMapping("/pedido/{idPedido}")
    public List<DetallePedidoDTO> obtenerDetallesPorPedido(@PathVariable Long idPedido) {
        return service.obtenerDetallesPorPedido(idPedido);
    }

    //  Crear detalle de pedido
    @PostMapping
    public DetallePedidoDTO guardarDetalle(@Valid @RequestBody DetallePedidoDTO detalleDTO) {
        return service.guardarDetalle(detalleDTO);
    }


    // Eliminar detalle de pedido
    @DeleteMapping("/{idDetalle}")
    public void eliminarDetalle(@PathVariable Long idDetalle) {
        service.eliminarDetalle(idDetalle);
    }
}