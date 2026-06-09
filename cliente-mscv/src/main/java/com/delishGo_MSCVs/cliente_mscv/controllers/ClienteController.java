package com.delishGo_MSCVs.cliente_mscv.controllers;

import com.delishGo_MSCVs.cliente_mscv.models.Cliente;
import com.delishGo_MSCVs.cliente_mscv.models.dtos.ClienteDTO;
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

    // Obtener todos los clientes
    @GetMapping
    public ResponseEntity<List<ClienteDTO>> findAll() {
        return ResponseEntity.ok(clienteService.findAll());
    }

    // Obtener cliente por ID
    @GetMapping("/{idCliente}")
    public ResponseEntity<ClienteDTO> findById(@PathVariable Long idCliente) {
        ClienteDTO cliente = clienteService.findByIdCliente(idCliente);
        return ResponseEntity.ok(cliente);
    }

    // Crear cliente
    @PostMapping
    public ResponseEntity<ClienteDTO> save(@Valid @RequestBody Cliente cliente) {
        ClienteDTO nuevo = clienteService.save(cliente);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevo);
    }

    // Eliminar cliente
    @DeleteMapping("/{idCliente}")
    public ResponseEntity<String> delete(@PathVariable Long idCliente) {
        clienteService.delete(idCliente);
        return ResponseEntity.ok("Cliente con ID: " + idCliente + " fue eliminado correctamente");
    }

    // Actualizar cliente
    @PutMapping("/{idCliente}")
    public ResponseEntity<ClienteDTO> update(@PathVariable Long idCliente,
                                             @Valid @RequestBody Cliente cliente) {
        ClienteDTO updated = clienteService.updateByIdCliente(idCliente, cliente);
        return ResponseEntity.ok(updated);
    }

    // 🔎 Listar clientes activos
    @GetMapping("/activos")
    public ResponseEntity<List<ClienteDTO>> findClientesActivos() {
        return ResponseEntity.ok(clienteService.findClientesActivos());
    }

    // 🔎 Buscar cliente por correo
    @GetMapping("/email/{emailCliente}")
    public ResponseEntity<ClienteDTO> findByEmail(@PathVariable String emailCliente) {
        ClienteDTO cliente = clienteService.findByEmailCliente(emailCliente);
        return ResponseEntity.ok(cliente);
    }


}
