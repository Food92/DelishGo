FROM eclipse-temurin:25-jdk-alpine
WORKDIR /app   
COPY target/restaurante-mscv-0.0.1-SNAPSHOT-exec.jar restaurante-mscv.jar
EXPOSE 8085
ENTRYPOINT ["java", "-jar", "restaurante-mscv.jar"]