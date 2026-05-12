package com.delishGo_MSCVs.pedido_mscv.services;

import com.delishGo_MSCVs.pedido_mscv.models.Pedido;
import com.delishGo_MSCVs.pedido_mscv.models.dtos.PedidoDTO;

import java.util.List;

public interface PedidoService {
    List<Pedido>findAll();
    Pedido findById(Long idCliente);
    Pedido save(PedidoDTO pedidoDTO);
    Pedido update(PedidoDTO  pedidoDTO, Long idCliente);
    void deleteById(Long id);
}
