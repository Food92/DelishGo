package com.delishGo_MSCVs.pedido_mscv.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity
@Table(name = "detalle_pedido")
public class DetallePedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "detalle_id")
    private Long detalleId;

    @NotNull(message = "El pedido no puede ser nulo")
    @Column(name = "pedido_id", nullable = false)
    private Long pedidoId; // solo guardas el ID del pedido

    @NotNull(message = "El producto no puede ser nulo")
    @Column(name = "producto_id", nullable = false)
    private Long productoId; // validado vía ProductoClient

    @NotNull(message = "La cantidad no puede ser nula")
    @Column(nullable = false)
    private Integer cantidad;

    @NotNull(message = "El precio unitario no puede ser nulo")
    @Column(name = "precio_unitario", nullable = false)
    private Double precioUnitario;

    @NotNull(message = "El subtotal no puede ser nulo")
    @Column(nullable = false)
    private Double subtotal;

    private String observacion;

    // Método para calcular subtotal
    public void calcularSubtotal() {
        if (cantidad != null && precioUnitario != null) {
            this.subtotal = cantidad * precioUnitario;
        } else {
            this.subtotal = 0.0;
        }
    }
}

