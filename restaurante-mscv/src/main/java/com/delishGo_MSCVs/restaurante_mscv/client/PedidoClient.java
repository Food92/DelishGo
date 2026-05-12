package com.delishGo_MSCVs.restaurante_mscv.client;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "pedido-mscv", url="localhost:8080/api/v1/pedidos")
public interface PedidoClient {

}
