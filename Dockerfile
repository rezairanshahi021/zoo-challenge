# ==============================
# Stage 1 — Build the JAR
# ==============================
FROM maven:3.9.9-eclipse-temurin-21 AS build
WORKDIR /app

# Copy pom and sources
COPY pom.xml .
RUN mvn dependency:go-offline

COPY src ./src
RUN mvn clean package -DskipTests

# ==============================
# Stage 2 — Runtime image
# ==============================
FROM eclipse-temurin:21-jre

# Metadata
LABEL maintainer="reza.iranshahi91@gmail.com"
LABEL project="zoo-app"

WORKDIR /app

# Copy built JAR
COPY --from=build /app/target/zoochallenge-1.0.0.jar app.jar

# Expose port
EXPOSE 8080

# Health check
HEALTHCHECK --interval=30s --timeout=5s CMD curl -f http://localhost:8080/actuator/health || exit 1

# Default command
ENTRYPOINT ["java","-XX:+UseContainerSupport","-jar","/app/app.jar"]
