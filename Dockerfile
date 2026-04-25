# ---------- Stage 1 : Build ----------
FROM eclipse-temurin:25-jdk AS build

WORKDIR /app

# Install Maven
RUN apt-get update && apt-get install -y maven && rm -rf /var/lib/apt/lists/*

# Copy pom.xml first for caching
COPY pom.xml .

# Download dependencies
RUN mvn dependency:go-offline -B

# Copy project files
COPY src ./src

# Build jar
RUN mvn clean package -DskipTests


# ---------- Stage 2 : Run ----------
FROM eclipse-temurin:25-jdk-alpine

WORKDIR /app

# Copy jar from build stage
COPY --from=build /app/target/*.jar app.jar

# JVM settings
ENV JAVA_OPTS="-Xms512m -Xmx2g"

# Expose Spring Boot port
EXPOSE 8080

# Run app
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]