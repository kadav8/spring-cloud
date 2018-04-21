FROM openjdk:8-jdk-alpine
VOLUME /tmp
ADD target/notifications.jar app.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
EXPOSE 8082