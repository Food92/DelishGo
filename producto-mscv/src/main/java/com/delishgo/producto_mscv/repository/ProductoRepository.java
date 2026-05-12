package com.delishgo.producto_mscv.repository;


import com.delishgo.producto_mscv.models.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductoRepository extends JpaRepository<Producto, Long> {
   List<Producto> findByCategoria(String categoria);

   List<Producto> findByDisponibleTrue(Boolean disponible);

   Optional<Producto> findByNombreProducto(String nombreProducto);
}