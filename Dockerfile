# ---------- Stage 1: Build with Java 25 ----------
FROM eclipse-temurin:25-jdk AS build
WORKDIR /app

# Install Maven manually since maven:*-25 doesn't exist
RUN apt-get update && apt-get install -y maven

# Copy and build
COPY pom.xml .
RUN mvn dependency:go-offline -B
COPY src ./src
RUN mvn clean package -DskipTests

# ---------- Stage 2: Run ----------
FROM eclipse-temurin:25-jdk-alpine
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]