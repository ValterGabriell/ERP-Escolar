FROM openjdk:17-alpine
COPY ./target/FrequenciaAlunos-0.0.1-SNAPSHOT.jar /app/FrequenciaAlunos-0.0.1-SNAPSHOT.jar.jar
WORKDIR /app
CMD java -jar ${APP_NAME}.jar
EXPOSE 8080