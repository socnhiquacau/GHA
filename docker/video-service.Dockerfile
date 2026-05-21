FROM eclipse-temurin:17-jre

WORKDIR /app
ARG JAR_FILE=video-service/target/video-service-1.0.0-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar

EXPOSE 8081
EXPOSE 9091

ENTRYPOINT ["java", "-jar", "app.jar"]
