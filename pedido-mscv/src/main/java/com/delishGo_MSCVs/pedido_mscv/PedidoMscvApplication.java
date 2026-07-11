package com.delishGo_MSCVs.pedido_mscv;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients(basePackages = "com.delishGo_MSCVs.pedido_mscv.client")
public class PedidoMscvApplication {

	public static void main(String[] args) {
		SpringApplication.run(PedidoMscvApplication.class, args);
	}

}
