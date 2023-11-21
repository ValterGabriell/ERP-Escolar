# Stage 1: Build
FROM ubuntu:latest AS build

RUN apt-get update && \
    apt-get install -y openjdk-17-jdk maven && \
    rm -rf /var/lib/apt/lists/*

COPY . .

RUN mvn clean install -DskipTests

# Stage 2: Run
FROM openjdk:17-jdk-slim

EXPOSE 8080

COPY --from=build /target/FrequenciaAlunos-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]
