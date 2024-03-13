FROM openjdk:21-jdk-oracle
COPY target/Microservice1-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app.jar"]