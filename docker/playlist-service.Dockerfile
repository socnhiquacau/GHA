FROM eclipse-temurin:17-jre

WORKDIR /app
ARG JAR_FILE=playlist-service/target/playlist-service-1.0.0-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar

EXPOSE 8082
EXPOSE 9092

ENTRYPOINT ["java", "-jar", "app.jar"]
