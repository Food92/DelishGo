package com.delishGo_MSCVs.pedido_mscv.client;

import com.delishGo_MSCVs.pedido_mscv.models.dtos.DetallePedidoDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "detallepedido-mscv", url = "http://localhost:8083/api/v1/detallepedidos")
public interface DetallePedidoClient {

    // 🔹 Guardar un nuevo detalle de pedido
    @PostMapping
    void guardarDetalle(@RequestBody DetallePedidoDTO detalle);

    // 🔹 Obtener todos los detalles de un pedido específico
    @GetMapping("/pedido/{idPedido}")
    List<DetallePedidoDTO> obtenerDetallesPorPedido(@PathVariable("idPedido") Long idPedido);
}
