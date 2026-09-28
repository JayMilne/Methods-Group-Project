FROM mcr.microsoft.com/openjdk/jdk:21-ubuntu
COPY ./target/classes/com /tmp/com
WORKDIR /tmp
ENTRYPOINT ["java", "com.napier.sem.Main"]