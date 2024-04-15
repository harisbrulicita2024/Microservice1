FROM openjdk:21-rc-jdk-slim

WORKDIR /app

COPY build/libs/micro2notfinal-1.0-SNAPSHOT.jar /app/app.jar

EXPOSE 8080

CMD ["java", "-jar", "/app/app.jar"]