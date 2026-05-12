package com.delishGo_MSCVs.pedido_mscv.client;

import com.delishGo_MSCVs.pedido_mscv.models.dtos.ProductoDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "producto-mscv", url="localhost:8082/api/v1/productos")
public interface ProductoClient {
    @GetMapping
    List<ProductoDTO> getAllProductos();

    @GetMapping("/categoria/{categoria}")
    List<ProductoDTO> getProductosByCategoria(@PathVariable String categoria);

    @GetMapping("/{idProducto}")
    ProductoDTO getProductoById(@PathVariable("idProducto") Long idProducto);
}
