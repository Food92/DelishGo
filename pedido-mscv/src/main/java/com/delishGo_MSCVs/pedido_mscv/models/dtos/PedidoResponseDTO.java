package com.delishGo_MSCVs.pedido_mscv.models.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import jdk.jfr.Name;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
@ToString
@NoArgsConstructor

public class PedidoResponseDTO {
    private Long idPedido;
    private String estado;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm")
    private LocalDateTime horaPedido;
    private Long idCliente;
    private Long idRestaurant;
    private List<DetallePedidoDTO> detallesPedido;
    private Double montoTotal;
}
