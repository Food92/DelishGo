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

    @JsonIgnore
    @Column(nullable = false)
    private Double precio;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm")
    private LocalDateTime horaPedido;


    @NotNull(message = "El campo cliente no puede ser vacio")
    @Column(nullable = false)
    private Long idCliente;

    @NotNull(message = "El campo de restaurant no puede ser vacio")
    @Column(nullable = false)
    private Long idRestaurant;

    @Transient
    private List<DetallePedidoDTO> detalles;

    //Calcula monto total
    public  Double calcularTotal(){
        if(detalles == null || detalles.isEmpty()){
            return 0.0;
        }
        return detalles.stream().mapToDouble(DetallePedidoDTO::getSubtotal).sum();
    }

    private Audit audit = new Audit();

}
