package com.delishGo_MSCVs.pedido_mscv.client;

import com.delishGo_MSCVs.pedido_mscv.models.dtos.ClienteDTO;
import com.delishGo_MSCVs.pedido_mscv.models.dtos.ProductoDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "cliente-service", url = "http://localhost:8081/api/v1/clientes")
public interface ClienteClient {
    @GetMapping("/{id}")
    ClienteDTO getClienteById(@PathVariable("id") Long id);
}


