package com.delishGo_MSCVs.pedido_mscv.models.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@NoArgsConstructor
public class PersonaDTO {
    private String idCliente;
    private String nombreCliente;
    private String apellidoCliente;
}
