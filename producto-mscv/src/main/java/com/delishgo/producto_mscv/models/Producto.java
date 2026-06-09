package com.delishgo.producto_mscv.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@NoArgsConstructor
@Entity
@Table(name = "productos")
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idProducto;


    @NotNull(message = "El id del restaurante no puede ser nulo")
    @Column(name="id_restaurante", nullable = false)
    private Long idRestaurante;

    @NotNull(message = "El nombre del producto no puede estar vacío")
    @Column(name="nombre_producto", nullable = false)
    private String nombreProducto;

    @Column(name="descripcion")
    private String descripcion;

    @NotNull(message = "El precio no puede ser nulo")
    @Column(name="precio", nullable = false)
    private Double precio;

    @NotNull(message = "La categoría no puede estar vacía")
    @Column(name="categoria", nullable = false)
    private String categoria;

    @NotNull(message = "El campo disponible no puede ser nulo")
    @Column(name="disponible", nullable = false)
    private Boolean disponible;

}
