# =====================================================
# Dockerfile cho Product Service
# =====================================================

# =====================================================
# STAGE 1: BUILD STAGE
FROM maven:3.9-eclipse-temurin-17 AS build

# Set working directory trong container
WORKDIR /app

# Copy file pom.xml trước để tận dụng Docker layer caching
# Khi pom.xml không thay đổi, layer này sẽ được cache
#COPY pom.xml .
# First, copy pom.xml
COPY pom.xml .


# Download tất cả dependencies được định nghĩa trong pom.xml
# Flag -B (batch mode) để chạy non-interactive
# Layer này sẽ được cache nếu pom.xml không đổi
RUN mvn dependency:go-offline -B

# Copy toàn bộ source code vào container
# Folder src chứa: java code, resources, proto files
COPY src ./src

# Build application với Maven
# clean: Xóa target folder cũ
# package: Compile code và tạo JAR file
# -DskipTests: Bỏ qua chạy unit tests để build nhanh hơn
RUN mvn clean package -DskipTests

# =====================================================
# STAGE 2: RUNTIME STAGE
# Chỉ chứa runtime environment và JAR file đã build
# Image này nhỏ hơn nhiều so với build stage
# =====================================================
FROM eclipse-temurin:17-jre-jammy

# Set working directory cho runtime
WORKDIR /app

# Tạo group và user không có quyền root để chạy application
# Tăng security bằng cách không chạy với root privileges
# -r: system account
# -g: specify group
RUN groupadd -r appuser && useradd -r -g appuser appuser

# Download OpenTelemetry Java Agent từ GitHub releases
# Agent này sẽ tự động instrument application để collect traces
# ADD command tự động download file từ URL
ADD https://github.com/open-telemetry/opentelemetry-java-instrumentation/releases/latest/download/opentelemetry-javaagent.jar /app/opentelemetry-javaagent.jar

# Set permission cho OpenTelemetry agent file
# 644: owner có quyền read/write, group và others chỉ read
RUN chmod 644 /app/opentelemetry-javaagent.jar

# Copy JAR file đã build từ build stage
# --from=build: lấy file từ stage có tên "build"
# *.jar: Copy bất kỳ JAR file nào trong target folder
# Rename thành app.jar cho dễ quản lý
COPY --from=build /app/target/*.war /app/app.war

# Thay đổi ownership của /app folder cho appuser
# Đảm bảo non-root user có quyền truy cập files
RUN chown -R appuser:appuser /app

# Switch sang non-root user để chạy application
# Từ đây trở đi, mọi lệnh chạy với quyền của appuser
USER appuser

# Expose ports để container có thể nhận traffic
# 8080: HTTP port cho REST API
# 9090: gRPC port cho gRPC services
EXPOSE 8080 9090

# Định nghĩa environment variables với giá trị default
# JAVA_OPTS: JVM options
#   -Xms256m: Initial heap size 256MB
#   -Xmx512m: Maximum heap size 512MB
ENV JAVA_OPTS="-Xms256m -Xmx512m"

# OTEL_JAVAAGENT_ENABLED: Bật/tắt OpenTelemetry agent
ENV OTEL_JAVAAGENT_ENABLED=false

# OTEL_SERVICE_NAME: Tên service trong tracing system
ENV OTEL_SERVICE_NAME=product-service

# OTEL_EXPORTER_OTLP_ENDPOINT: Địa chỉ Jaeger/OTLP collector
# Default là localhost, thay đổi khi deploy production
#ENV OTEL_EXPORTER_OTLP_ENDPOINT=http://localhost:4317

# Health check để Docker/Kubernetes biết container có healthy không
# --interval=30s: Check mỗi 30 giây
# --timeout=3s: Timeout sau 3 giây
# --start-period=60s: Đợi 60 giây để application start xong
# --retries=3: Thử 3 lần trước khi mark unhealthy
# CMD: Chạy curl để check /actuator/health endpoint
# exit 1: Return error code nếu health check fail
HEALTHCHECK --interval=30s --timeout=3s --start-period=60s --retries=3 \
    CMD curl -f http://localhost:8080/actuator/health || exit 1

# ENTRYPOINT: Command chạy khi container start
# sh -c: Execute command trong shell
# $JAVA_OPTS: Inject JVM options từ environment variable
# -javaagent: Attach OpenTelemetry agent để auto-instrumentation
# -jar: Chạy JAR file
# Kết quả: java -Xms256m -Xmx512m -javaagent:/app/opentelemetry-javaagent.jar -jar /app/app.jar
#ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -javaagent:/app/opentelemetry-javaagent.jar -jar /app/app.war"]
ENTRYPOINT ["sh", "-c", "java -jar /app/app.war"]


# =====================================================
# Build Instructions:
#
# Build image:
#   docker build -t product-service:latest .
#
# Run container:
#   docker run -p 8080:8080 -p 9090:9090 \
#     -e DB_URL=jdbc:mysql://host.docker.internal:3306/product_db \
#     -e DB_USERNAME=root \
#     -e DB_PASSWORD=password \
#     product-service:latest
#
# Build with custom Java options:
#   docker build --build-arg JAVA_OPTS="-Xms512m -Xmx1024m" \
#     -t product-service:latest .
# =====================================================