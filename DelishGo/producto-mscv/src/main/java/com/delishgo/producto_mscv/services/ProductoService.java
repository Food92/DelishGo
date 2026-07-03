package com.delishgo.producto_mscv.services;

import com.delishgo.producto_mscv.models.Producto;
import com.delishgo.producto_mscv.models.dtos.ProductoDTO;

import java.util.List;
import java.util.Optional;

public interface ProductoService {
    List<ProductoDTO> findAll();
    Producto findById(Long id);
    Producto save(Producto producto);
    Producto updateById(Producto producto, Long id);
    void deleteById(Long id);

    // filtros adicionales
    Optional<Producto> findByNombre(String nombre);
    List<Producto> findByCategoria(String categoria);
    List<Producto> findByDisponible(Boolean disponible);

    // 🔗 nuevo: productos por restaurante
    List<Producto> findByIdRestaurante(Long idRestaurante);
}


