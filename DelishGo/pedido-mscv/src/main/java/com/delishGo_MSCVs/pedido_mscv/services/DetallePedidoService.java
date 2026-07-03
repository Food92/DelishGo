package com.delishGo_MSCVs.pedido_mscv.services;

import com.delishGo_MSCVs.pedido_mscv.models.dtos.DetallePedidoDTO;

import java.util.List;

public interface DetallePedidoService {
    List<DetallePedidoDTO> obtenerDetallesPorPedido(Long idPedido);
    DetallePedidoDTO guardarDetalle(DetallePedidoDTO detalleDTO);
    void eliminarDetalle(Long idDetalle);
}
