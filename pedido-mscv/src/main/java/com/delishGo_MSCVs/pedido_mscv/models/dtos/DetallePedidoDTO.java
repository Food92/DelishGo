package com.delishGo_MSCVs.pedido_mscv.models.dtos;

import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class DetallePedidoDTO {

    private Long idPedido;
    private Long idDetalle;
    private Long idProducto;        // ID del producto
    private Integer cantidad;       // cantidad solicitada
    private Double precioUnitario;  // precio del producto
    private Double subtotal;        // cantidad * precioUnitario
    private String observacion;     // comentario opcional del cliente

    // Calcular subtotal dinámicamente
    public Double getSubtotal() {
        return (cantidad != null && precioUnitario != null)
                ? cantidad * precioUnitario
                : 0.0;
    }

}

