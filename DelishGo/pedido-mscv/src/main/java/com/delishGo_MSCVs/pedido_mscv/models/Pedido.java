package com.delishGo_MSCVs.pedido_mscv.models;

import com.delishGo_MSCVs.pedido_mscv.models.dtos.DetallePedidoDTO;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "pedidos")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPedido;

    @NotNull(message = "El campo de estado no puede ser vacío")
    @Column(nullable = false)
    private String estado;

    @Column(nullable = false)
    private Double montoTotal; // ✅ total del pedido

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm")
    private LocalDateTime horaPedido;

    @NotNull(message = "El campo cliente no puede ser vacío")
    @Column(nullable = false)
    private Long idCliente;

    @NotNull(message = "El campo restaurante no puede ser vacío")
    @Column(nullable = false)
    private Long idRestaurant;

    @Transient
    private List<DetallePedidoDTO> detalles; // ✅ no se persiste, viene del microservicio de detalles

    // Calcula monto total
    public Double calcularTotal() {
        if (detalles == null || detalles.isEmpty()) {
            return 0.0;
        }
        return detalles.stream().mapToDouble(DetallePedidoDTO::getSubtotal).sum();
    }




    private Audit audit = new Audit();
}
