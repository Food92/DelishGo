package com.delishGo_MSCVs.restaurante_mscv.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Setter
@Getter
@NoArgsConstructor
@ToString
@Table(name = "restaurants")

public class Restaurant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_restaurant")
    private Long idRestaurant;

    @Column(name = "nombre_restaurant", nullable = false)
    @NotBlank(message = "El campo run no puede ser vacio")
    private String nombreRestaurant;

    @Column(name = "categoria", nullable = false)
    @NotBlank(message = "El campo descripcion no puede ser vacio")
    private String categoria;

    @Column(name = "direccion")
    @NotBlank(message = "El campo de direccion no se puede vacio")
    private String direccion;

    @Column(nullable = false, unique = true)
    @Email(message = "El correo tiene que tener formato de correo")
    @NotBlank(message = "El correo no puede ser vacio")
    private String correo;//tipo de comida o especialidad (ejemplo: italiana, sushi, gourmet).

    @Embedded
    private Audit audit = new Audit();

}
