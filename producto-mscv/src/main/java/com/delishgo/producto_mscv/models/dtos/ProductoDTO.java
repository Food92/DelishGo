package com.delishgo.producto_mscv.models.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@NoArgsConstructor
public class ProductoDTO {
    private Long idProducto;
    private String nombreProducto;
    private String descripcion;
    private Double precio;
    private String categoria;
    private Boolean disponible;
    private Long idRestaurante;
}
