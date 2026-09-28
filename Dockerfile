FROM mcr.microsoft.com/openjdk/jdk:21-ubuntu
COPY ./target/semApp.jar /tmp
WORKDIR /tmp
ENTRYPOINT ["java", "-jar", "semApp.jar"]