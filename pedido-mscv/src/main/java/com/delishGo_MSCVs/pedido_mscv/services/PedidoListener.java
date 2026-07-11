package com.delishGo_MSCVs.pedido_mscv.services;

import com.delishGo_MSCVs.pedido_mscv.models.dtos.PedidoResponseDTO;
import com.delishGo_MSCVs.pedido_mscv.repositories.PedidoRepository;
import io.awspring.cloud.sqs.annotation.SqsListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PedidoListener {

    @Autowired
    private PedidoRepository pedidoRepository;

    // Escucha automáticamente la misma cola que definiste en tus properties
    @SqsListener("${AWS_SQS_QUEUE_URL}")
    public void procesarPedidoDesdeCola(PedidoResponseDTO pedidoDTO) {
        try {
            System.out.println("📥 ====== NUEVO PEDIDO RECIBIDO DESDE SQS ======");
            System.out.println("Procesando Pedido ID: " + pedidoDTO.getIdPedido() + " del Cliente: " + pedidoDTO.getIdCliente());

            // Aquí se simula el procesamiento (por ejemplo, cambiar el estado a ENTREGADO o EN_CAMINO)
            pedidoRepository.findById(pedidoDTO.getIdPedido()).ifPresent(pedido -> {
                pedido.setEstado("PROCESADO");
                pedidoRepository.save(pedido);
                System.out.println("✅ ====== PEDIDO ACTUALIZADO A 'PROCESADO' EN BASE DE DATOS ======");
            });

        } catch (Exception e) {
            System.err.println("❌ Error al procesar el mensaje de SQS: " + e.getMessage());
        }
    }
}
