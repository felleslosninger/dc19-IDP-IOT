FROM openjdk:8-jdk-alpine
COPY ./target/idporten-1.0-SNAPSHOT.jar /usr/app/
WORKDIR /usr/app/
RUN sh -c "touch idporten-1.0-SNAPSHOT.jar"
ENTRYPOINT ["java", "-Dcom.amazon.speech.speechlet.servlet.timestampTolerance=150", "-Dcom.amazon.speech.speechlet.servlet.supportedApplicationIds=amzn1.ask.skill.737f8e88-5d1a-48d6-8445-ce986f6b7c05", "-jar", "idporten-1.0-SNAPSHOT.jar"]