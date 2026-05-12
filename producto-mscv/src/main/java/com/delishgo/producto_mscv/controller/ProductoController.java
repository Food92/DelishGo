package com.delishgo.producto_mscv.controller;

import com.delishgo.producto_mscv.models.Producto;
import com.delishgo.producto_mscv.models.dtos.ProductoDTO;
import com.delishgo.producto_mscv.services.ProductoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/productos")
public class ProductoController {
    @Autowired
    private ProductoService productoService;

    @GetMapping
    public ResponseEntity<List<ProductoDTO>> getAllProductos() {
        return ResponseEntity.ok(productoService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Producto> getProducto(@PathVariable Long id) {
        return ResponseEntity.ok(productoService.findById(id));
    }



    @PutMapping("/{id}")
    public ResponseEntity<Producto> updateProducto(@Valid @RequestBody Producto producto, @PathVariable Long id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(productoService.updateById(producto, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProducto(@PathVariable Long id) {
        productoService.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<Optional<Producto>> getByNombre(@PathVariable String nombre) {
        return ResponseEntity.ok(productoService.findByNombre(nombre));
    }

    @GetMapping("/categoria/{categoria}")
    public ResponseEntity<List<Producto>> getByCategoria(@PathVariable String categoria) {
        return ResponseEntity.ok(productoService.findByCategoria(categoria));
    }

    @GetMapping("/disponible/{disponible}")
    public ResponseEntity<List<Producto>> getByDisponible(@PathVariable Boolean disponible) {
        return ResponseEntity.ok(productoService.findByDisponible(disponible));
    }

    @PostMapping
    public ResponseEntity<Producto> saveProducto(@RequestBody Producto producto) {
        Producto saved = productoService.save(producto);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }
}
