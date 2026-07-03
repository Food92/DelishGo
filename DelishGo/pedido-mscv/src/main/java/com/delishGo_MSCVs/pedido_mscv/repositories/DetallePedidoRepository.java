package com.delishGo_MSCVs.pedido_mscv.repositories;

import com.delishGo_MSCVs.pedido_mscv.models.DetallePedido;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DetallePedidoRepository extends JpaRepository<DetallePedido, Long> {
    List<DetallePedido> findByIdPedido(Long idPedido);
}
