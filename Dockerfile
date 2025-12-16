# Multi-stage Dockerfile for building and running the Spring Boot (Gradle) application
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
EXPOSE 8080
COPY --from=build /home/gradle/project/build/libs/*.jar app.jar
# Copy jar from builder
WORKDIR /app
FROM eclipse-temurin:17-jre-alpine
# Run stage

RUN gradle clean bootJar --no-daemon
COPY --chown=gradle:gradle . /home/gradle/project
WORKDIR /home/gradle/project
FROM gradle:8.5-jdk17 AS build
# Build stage

