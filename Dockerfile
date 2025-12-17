# ---------- BUILD STAGE ----------
FROM gradle:8.5-jdk17 AS build
WORKDIR /home/gradle/project

COPY --chown=gradle:gradle gradlew .
COPY --chown=gradle:gradle gradle gradle
COPY --chown=gradle:gradle build.gradle settings.gradle ./

RUN gradle dependencies --no-daemon

COPY --chown=gradle:gradle src src
RUN gradle bootJar --no-daemon

# ---------- RUNTIME STAGE ----------
FROM eclipse-temurin:17-jre
WORKDIR /app

RUN useradd -r -u 1001 spring
USER spring

COPY --from=build /home/gradle/project/build/libs/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-XX:MaxRAMPercentage=75", "-jar", "app.jar"]
