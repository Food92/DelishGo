package com.delishGo_MSCVs.pedido_mscv.repositories;

import com.delishGo_MSCVs.pedido_mscv.models.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {
}
