FROM openjdk:8-jdk-alpine
VOLUME /tmp
ADD target/eureka.jar app.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
EXPOSE 2222