# Stage 1: Build the application
FROM gradle:8.5-jdk17 AS builder
WORKDIR /app
COPY . .
RUN gradle build

# Stage 2: Run the application
FROM eclipse-temurin:17-jre-jammy
WORKDIR /app
COPY --from=builder /app/build/libs/*.jar app.jar

# Create a non-root user
RUN useradd -r -u 1001 -g root springuser && \
    chown springuser:root app.jar
USER springuser

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
