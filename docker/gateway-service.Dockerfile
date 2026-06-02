FROM eclipse-temurin:17-jre

WORKDIR /app
ARG JAR_FILE=gateway-service/target/gateway-service-1.0.0-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar

EXPOSE 8080
EXPOSE 9090

ENTRYPOINT ["java", "-jar", "app.jar"]
