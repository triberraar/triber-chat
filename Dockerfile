FROM openjdk:8-jdk-alpine

ARG version

COPY ./target/triber-chat-${version}.jar app.jar
RUN sh -c 'touch /app.jar'

ENTRYPOINT ["java", "-jar", "/app.jar"]