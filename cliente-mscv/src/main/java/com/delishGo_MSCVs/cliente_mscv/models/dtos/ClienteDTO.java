package com.delishGo_MSCVs.cliente_mscv.models.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@NoArgsConstructor
@ToString
public class ClienteDTO {
    private Long idCliente;
    private String run;
    private String nombreCliente;
    private String apellidoCliente;
    private String direccionCliente;
    private String telefono;
    private String emailCliente;
}
