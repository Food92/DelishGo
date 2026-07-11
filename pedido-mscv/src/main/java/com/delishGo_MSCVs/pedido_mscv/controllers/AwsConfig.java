package com.delishGo_MSCVs.pedido_mscv.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider; // 👈 Importa este
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sqs.SqsAsyncClient;

@Configuration
public class AwsConfig {

    @Value("${spring.cloud.aws.region.static:us-east-1}") // Si no encuentra la propiedad, usa us-east-1 por defecto
    private String region;

    @Bean
    @Primary
    public SqsAsyncClient sqsAsyncClient() {
        return SqsAsyncClient.builder()
                .region(Region.of(region))
                .credentialsProvider(DefaultCredentialsProvider.create()) // 👈 Usa el proveedor por defecto
                .build();
    }
}