package com.delishGo_MSCVs.pedido_mscv.models.dtos;

import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@NoArgsConstructor
@ToString
public class ProductoDTO {
    private Long idProducto;
    private String nombreProducto;
    private String descripcion;
    private Double precio;
    private String categoria;
    private Boolean disponible;
}
