package com.delishGo_MSCVs.pedido_mscv.client;

import com.delishGo_MSCVs.pedido_mscv.models.dtos.RestaurantDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "restaurant-mscv", url="localhost:8080/api/v1/restaurants")
public interface RestaurantClient {
    @GetMapping("/{id}")
    RestaurantDTO getRestaurantById(@PathVariable Long id);
}
