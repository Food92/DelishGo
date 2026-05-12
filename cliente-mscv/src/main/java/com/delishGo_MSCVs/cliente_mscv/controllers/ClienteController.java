package com.delishGo_MSCVs.cliente_mscv.controllers;

import com.delishGo_MSCVs.cliente_mscv.models.Cliente;
import com.delishGo_MSCVs.cliente_mscv.services.ClienteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/clientes")
@Validated

public class ClienteController {
    @Autowired
    private ClienteService clienteService;

    @GetMapping
    public ResponseEntity<List<Cliente>>findAll(){
        return ResponseEntity.ok(clienteService.findAll());
    }

    @GetMapping("/{idCliente}")
    public ResponseEntity<Cliente> findById(@PathVariable Long idCliente) {
        Cliente cliente = clienteService.findByIdCliente(idCliente);
        if (cliente == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(cliente);
    }

    @PostMapping
    public ResponseEntity<String> save(@Valid @RequestBody Cliente cliente) {
        Cliente nuevo = clienteService.save(cliente);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Cliente " + nuevo.getNombreCliente() + " agregado correctamente con ID:" + nuevo.getIdCliente());
    }


    @DeleteMapping("/{idCliente}")
    public ResponseEntity<String> delete(@PathVariable Long idCliente) {
        clienteService.delete(idCliente);
        return ResponseEntity.ok("Cliente con ID:" + idCliente + "fue eliminado correctamente");
    }


    @PutMapping("/{idCliente}")
    public ResponseEntity<Cliente> update(@PathVariable Long idCliente,
                                          @Valid @RequestBody Cliente cliente) {
        Cliente updated = clienteService.updateByIdCliente(idCliente, cliente);
        return ResponseEntity.ok(updated);
    }


}
