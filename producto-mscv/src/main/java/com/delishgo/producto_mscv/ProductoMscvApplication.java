package com.delishgo.producto_mscv;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class ProductoMscvApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProductoMscvApplication.class, args);
	}

}
