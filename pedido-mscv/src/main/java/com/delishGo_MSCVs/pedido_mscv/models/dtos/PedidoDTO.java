package com.delishGo_MSCVs.pedido_mscv.models.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


import java.util.List;

@Setter
@Getter
@ToString
@NoArgsConstructor
public class PedidoDTO {
    private Long idCliente;
    private Long idRestaurant;
    private List<DetallePedidoDTO> detallesPedido;
    private Double montoTotal;
    private String estado;
}
