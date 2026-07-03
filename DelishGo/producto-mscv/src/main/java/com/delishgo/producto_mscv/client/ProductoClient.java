package com.delishgo.producto_mscv.client;

import com.delishgo.producto_mscv.models.dtos.ProductoDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "producto-mscv", url="localhost:8082/pi/v1/productos")
public interface ProductoClient {
    @GetMapping("/{id}")
    ProductoDTO getProductoById(@PathVariable Long id);
}
