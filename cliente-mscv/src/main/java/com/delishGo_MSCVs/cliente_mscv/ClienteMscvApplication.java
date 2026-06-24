package com.delishGo_MSCVs.cliente_mscv;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class ClienteMscvApplication {

	public static void main(String[] args) {
		SpringApplication.run(ClienteMscvApplication.class, args);
	}

}
