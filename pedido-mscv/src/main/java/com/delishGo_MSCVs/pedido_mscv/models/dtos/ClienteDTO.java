package com.delishGo_MSCVs.pedido_mscv.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@AllArgsConstructor
public class ClienteDTO {
    private Long idCliente;
    private String run;
    private String nombreCliente;
    private String apellidoCliente;
    private String direccionCliente;
    private String telefono;
    private String emailCliente;
    private String categoria;
}
