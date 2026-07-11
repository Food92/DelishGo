package com.delishGo_MSCVs.pedido_mscv.services;

import com.delishGo_MSCVs.pedido_mscv.models.Pedido;
import com.delishGo_MSCVs.pedido_mscv.models.dtos.PedidoDTO;
import com.delishGo_MSCVs.pedido_mscv.models.dtos.PedidoResponseDTO;

import java.util.List;

public interface PedidoService {
    List<PedidoResponseDTO> findAll();
    PedidoResponseDTO findById(Long id);
    PedidoResponseDTO save(PedidoDTO pedidoDTO);
    PedidoResponseDTO update(Long id, PedidoDTO pedidoDTO);
    void deleteById(Long id);

    // 🔎 Filtros adicionales
    List<PedidoResponseDTO> findByIdCliente(Long idCliente);
    List<PedidoResponseDTO> findByIdRestaurant(Long idRestaurant);
    List<PedidoResponseDTO> findByEstado(String estado);

    PedidoResponseDTO procesarSiguientePedido();
}


