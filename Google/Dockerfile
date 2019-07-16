FROM openjdk:8-jdk-alpine
COPY ./target/GoogleTest-0.0.1-SNAPSHOT.jar /usr/app/
WORKDIR /usr/app/
RUN sh -c "touch GoogleTest-0.0.1-SNAPSHOT.jar"
ENTRYPOINT ["java","-jar", "GoogleTest-0.0.1-SNAPSHOT.jar"]


