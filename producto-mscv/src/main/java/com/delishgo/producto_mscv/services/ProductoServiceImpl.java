package com.delishgo.producto_mscv.services;

import com.delishgo.producto_mscv.exceptions.ProductoException;
import com.delishgo.producto_mscv.models.Producto;
import com.delishgo.producto_mscv.models.dtos.ProductoDTO;
import com.delishgo.producto_mscv.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductoServiceImpl implements ProductoService{
    @Autowired
    private ProductoRepository productoRepository;

    @Override
    public List<ProductoDTO> findAll() {
        return productoRepository.findAll().stream().map(p->{
            ProductoDTO dto = new ProductoDTO();
            dto.setIdProducto(p.getIdProducto());
            dto.setNombreProducto(p.getNombreProducto());
            dto.setDescripcion(p.getDescripcion());
            dto.setPrecio(p.getPrecio());
            dto.setCategoria(p.getCategoria());
            dto.setDisponible(p.getDisponible());
            return dto;
        }).toList();
    }

    @Override
    public Producto findById(Long id) {
        return productoRepository.findById(id).orElseThrow(
                ()-> new ProductoException("El pedido con ID: " + id + " no existe"));
    }

    @Override
    public Producto save(Producto producto) {
        // Validación: que no exista otro producto con el mismo nombre
        if (productoRepository.findByNombreProducto(producto.getNombreProducto()).isPresent()) {
            throw new ProductoException("El producto con nombre " + producto.getNombreProducto() + " ya existe");
        }
        // Validación: precio no nulo ni negativo
        if (producto.getPrecio() == null || producto.getPrecio() <= 0) {
            throw new ProductoException("El precio del producto debe ser mayor a 0");
        }
        // Validación: categoría obligatoria
        if (producto.getCategoria() == null || producto.getCategoria().isBlank()) {
            throw new ProductoException("La categoría del producto no puede ser vacía");
        }
        // Guardar producto
        return productoRepository.save(producto);
    }

    @Override
    public Producto updateById(Producto producto, Long id) {
        return productoRepository.findById(id).map(p -> {
            p.setNombreProducto(producto.getNombreProducto());
            p.setDescripcion(producto.getDescripcion());
            p.setPrecio(producto.getPrecio());
            p.setCategoria(producto.getCategoria());
            p.setDisponible(producto.getDisponible());
            return productoRepository.save(p);
        }).orElseThrow(() -> new ProductoException("El producto con id " + id + " no existe"));
    }

    @Override
    public void deleteById(Long id) {
        productoRepository.deleteById(id);
    }
    @Override
    public Optional<Producto> findByNombre(String nombre) {
        return productoRepository.findByNombreProducto(nombre);
    }

    @Override
    public List<Producto> findByCategoria(String categoria) {
        return productoRepository.findByCategoria(categoria);
    }

    @Override
    public List<Producto> findByDisponible(Boolean disponible) {
        return productoRepository.findByDisponibleTrue(disponible);
    }
}
