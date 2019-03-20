FROM maven:3.3-jdk-8 as builder

ADD . /backend-challenge/

RUN cd /backend-challenge; mvn clean install

FROM java:8

ADD . /

EXPOSE  5000

ENTRYPOINT ["java" , "-Xmx1g", "-jar", "/target/backend-challenge-1.0-SNAPSHOT.jar"]
