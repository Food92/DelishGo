package com.delishGo_MSCVs.pedido_mscv.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Setter
@Getter
@NoArgsConstructor
@Table(name = "pedidos")
@ToString
public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPedido;

    @NotNull(message = "El campo de estado no puede ser vacio")
    @Column(nullable = false)
    private String estado;

    @NotNull(message = "El campo de costo no puede ser vacio")
    @Column(nullable = false)
    private Double precio;

    @NotNull(message = "El campo de hora no puede ser vacio")
    private LocalDateTime HoraPedido;

    @NotNull(message = "El campo cliente no puede ser vacio")
    @Column(nullable = false)
    private Long idCliente;

    @NotNull(message = "El campo de restaurant no puede ser vacio")
    @Column(nullable = false)
    private Long idRestaurant;

    private Audit audit = new Audit();

}
