FROM openjdk:8-jdk-alpine
EXPOSE 8089
ADD target/tp-foyer-5.0.0-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]
