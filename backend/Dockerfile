# Use Maven with Java version 25 to build Spring Boot backend.
FROM maven:3.9.12-eclipse-temurin-25 AS build

# Set working directory inside container.
WORKDIR /app

# Copy project files needed to build.
COPY pom.xml .
COPY src ./src

# Build the backend and generate .jar file (skip tests for faster build).
RUN mvn clean package -DskipTests

# Use Java 25 to run the backend.
FROM eclipse-temurin:25-jre

# Set working directory.
WORKDIR /app

# Copy the .jar file from build stage.
COPY --from=build /app/target/*.jar app.jar

# Expose backend port.
EXPOSE 8080

# Run the Spring Boot backend.
ENTRYPOINT ["java", "-jar", "app.jar"]
