package com.delishGo_MSCVs.pedido_mscv.controllers;

import com.delishGo_MSCVs.pedido_mscv.models.Pedido;
import com.delishGo_MSCVs.pedido_mscv.models.dtos.PedidoDTO;
import com.delishGo_MSCVs.pedido_mscv.services.PedidoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/pedidos")
@Validated
public class PedidoController {
    @Autowired
    private PedidoService pedidoService;

    @GetMapping
    public ResponseEntity<List<Pedido>>findAll(){
        return ResponseEntity.status(HttpStatus.OK).body(this.pedidoService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pedido> findById(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(this.pedidoService.findById(id));
    }

    @PostMapping
    public ResponseEntity<Pedido> save(@Valid @RequestBody PedidoDTO pedidoDTO){
        return ResponseEntity.status(HttpStatus.OK).body(this.pedidoService.save(pedidoDTO));
    }

}
