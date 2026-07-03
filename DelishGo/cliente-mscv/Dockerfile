FROM eclipse-temurin:25-jre-alpine
WORKDIR /app
COPY target/cliente-mscv-0.0.1-SNAPSHOT-exec.jar cliente-mscv.jar
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "cliente-mscv.jar"]