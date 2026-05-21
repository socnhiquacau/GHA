FROM eclipse-temurin:17-jre

WORKDIR /app
ARG JAR_FILE=aggregation-worker/target/aggregation-worker-1.0.0-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar

EXPOSE 8083

ENTRYPOINT ["java", "-jar", "app.jar"]
