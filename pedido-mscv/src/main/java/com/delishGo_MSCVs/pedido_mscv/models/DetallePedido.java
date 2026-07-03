package com.delishGo_MSCVs.pedido_mscv.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "detalles_pedido")
@Getter
@Setter
@NoArgsConstructor
public class DetallePedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idDetalle;

    @NotNull
    @Column(nullable = false)
    private Long idPedido;

    @NotNull
    @Column(nullable = false)
    private Long idProducto;

    @NotNull
    @Column(nullable = false)
    private Integer cantidad;

    @NotNull
    @Column(nullable = false)
    private Double precioUnitario;

    @Column(nullable = false)
    private Double subtotal;

    private String observacion;

    // Calcular subtotal automáticamente
    public void calcularSubtotal() {
        if (cantidad != null && precioUnitario != null) {
            this.subtotal = cantidad * precioUnitario;
        }
    }
}
