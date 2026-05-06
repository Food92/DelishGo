package com.delishGo_MSCVs.cliente_mscv.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Setter
@Getter
@ToString
@NoArgsConstructor
@Table(name = "clientes")
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cliente")
    private Long idCliente;

    @Column(unique = true, nullable = false)
    @NotBlank(message="El campo run no puede ser vacio")
    @Pattern(regexp = "\\d{7,8}-[\\dkK]$", message = "El formato del run debe ser xxxxxxxx-x")
    private String run;

    @Column(name = "nombre_cliente", nullable = false)
    @NotBlank(message = "El campo nombreCompleto no puede ser vacio")
    private String nombreCliente;

    @Column(name = "apellido_cliente", nullable = false)
    @NotBlank(message = "El campo apellido no puede ser vacio")
    private String apellidoCliente;

    @Column(name = "direccion_cliente", nullable = false)
    @NotBlank(message = "La direccion no puede ser vacio")
    private String direccionCliente;

    @Pattern(
            regexp = "^(\\+56)?\\s?9\\d{8}$",
            message = "El formato del teléfono debe ser +56 9XXXXXXXX"
    )
    private String telefono;


    @Email(message = "El correo tiene que tener formato de correo")
    @Column(nullable = false, unique = true)
    @NotBlank(message = "El correo no puede ser vacio")
    private String emailCliente;

    @Embedded
    private Audit audit = new Audit();
}
