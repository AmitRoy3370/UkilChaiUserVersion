# ---------- BUILD STAGE ----------
FROM eclipse-temurin:25-jdk AS build

WORKDIR /app

# Copy Maven wrapper and Maven config
COPY .mvn .mvn
COPY mvnw pom.xml ./

# Give execute permission
RUN chmod +x mvnw

# Download dependencies
RUN ./mvnw dependency:go-offline

# Copy source code
COPY src ./src

# Build application
RUN ./mvnw clean package -DskipTests


# ---------- RUN STAGE ----------
FROM eclipse-temurin:25-jre

WORKDIR /app

# Copy generated jar from build stage
COPY --from=build /app/target/*.jar app.jar

# Expose Spring Boot port
EXPOSE 8080

# Run application
ENTRYPOINT ["java", "-jar", "app.jar"]