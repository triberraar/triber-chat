FROM openjdk:8-jdk-alpine

ARG tag

COPY ./target/triber-chat-$tag.jar app.jar
RUN sh -c 'touch /app.jar'

EXPOSE 8080

ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "/app.jar"]