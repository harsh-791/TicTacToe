# Multi-stage Dockerfile for Production-Grade TicTacToe Application
# Stage 1: Build stage - Compile and package the application
FROM maven:3.9-eclipse-temurin-19 AS build

# Set working directory
WORKDIR /app

# Copy pom.xml and checkstyle.xml first for better layer caching
COPY pom.xml .
COPY checkstyle.xml .

# Download dependencies (cached layer if pom.xml doesn't change)
RUN mvn dependency:go-offline -B

# Copy source code
COPY src ./src

# Build the application (skip tests and checkstyle in Docker build, they run in CI)
RUN mvn clean package -DskipTests -Dcheckstyle.skip=true -B

# Stage 2: Runtime stage - Minimal JRE image
FROM eclipse-temurin:19-jre-alpine

# Add metadata labels
LABEL maintainer="DevOps Team"
LABEL description="TicTacToe Console Application"
LABEL version="1.0.0"

# Create non-root user for security
RUN addgroup -S appgroup && adduser -S appuser -G appgroup

# Set working directory
WORKDIR /app

# Copy JAR from build stage
COPY --from=build /app/target/tictactoe-app.jar app.jar

# Change ownership to non-root user
RUN chown -R appuser:appgroup /app

# Switch to non-root user
USER appuser

# Health check (for container orchestration)
HEALTHCHECK --interval=30s --timeout=3s --start-period=5s --retries=3 \
    CMD java -jar app.jar --smoke-test || exit 1

# Default command
ENTRYPOINT ["java", "-jar", "app.jar"]
