# syntax=docker/dockerfile:1

# ---- Build stage ------------------------------------------------------------
FROM maven:3.9-eclipse-temurin-21 AS build

WORKDIR /workspace

# Copy the build descriptors first so dependency resolution can be cached
# independently of source code changes.
COPY pom.xml ./
COPY .mvn/ .mvn/
COPY mvnw ./

# The wrapper is authored on Windows and may use CRLF; normalize it before use.
RUN sed -i 's/\r$//' mvnw && chmod +x mvnw

# Cache project dependencies in a dedicated layer.
RUN ./mvnw -B -DskipTests dependency:resolve

COPY src/ src/

# Build the executable jar without running tests (tests run in the local build).
RUN ./mvnw -B -DskipTests package

# ---- Runtime stage ----------------------------------------------------------
FROM eclipse-temurin:21-jre

WORKDIR /app

# Run the application as a non-root user.
RUN groupadd --system app && useradd --system --gid app app

COPY --from=build --chown=app:app /workspace/target/borboremaimperial-*.jar app.jar

USER app

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/app.jar"]
