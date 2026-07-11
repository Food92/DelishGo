FROM eclipse-temurin:25-jdk-alpine
WORKDIR /app
COPY target/pedido-mscv-0.0.1-SNAPSHOT.jar pedido-mscv.jar  
EXPOSE 8083
ENTRYPOINT ["java", "-jar", "pedido-mscv.jar"]