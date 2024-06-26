FROM openjdk:21-rc-jdk-slim

WORKDIR /app

COPY target/Microservice1-0.0.1-SNAPSHOT.jar /app/app.jar

EXPOSE 9000

CMD ["java", "-jar", "/app/app.jar"]