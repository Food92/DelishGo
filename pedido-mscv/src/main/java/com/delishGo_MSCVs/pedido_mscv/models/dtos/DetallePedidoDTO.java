package com.delishGo_MSCVs.pedido_mscv.models.dtos;

import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
@ToString
public class DetallePedidoDTO {
    private Long idProducto;       // ID del producto
    private Long cantidad;      // cantidad solicitada
    private Double precioUnitario; // se calcula en el service
    private Double subtotal;       // cantidad * precioUnitario
    private String observacion;    // comentario opcional del cliente
}

