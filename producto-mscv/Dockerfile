FROM eclipse-temurin:25-jre-alpine
WORKDIR /app
COPY target/producto-mscv-0.0.1-SNAPSHOT.jar producto-mscv.jar
EXPOSE 8084
ENTRYPOINT ["java", "-jar", "producto-mscv.jar"]