package com.delishGo_MSCVs.pedido_mscv.models.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@NoArgsConstructor
public class RestaurantDTO {
    private Long idRestaurant;
    private String nombreRestaurant;
    private String direccion;
    private String categoria;
    private String correo;
}
