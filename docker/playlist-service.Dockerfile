FROM eclipse-temurin:17-jre

WORKDIR /app
ARG WAR_FILE=playlist-service/target/playlist-service-1.0.0-SNAPSHOT.war
COPY ${WAR_FILE} app.war

EXPOSE 8082
EXPOSE 9092

ENTRYPOINT ["java", "-jar", "app.war"]
