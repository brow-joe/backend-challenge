FROM maven:3.3-jdk-8 as builder

EXPOSE  5000

COPY . /backend-challenge
WORKDIR /backend-challenge

RUN mvn clean install -f /backend-challenge && mkdir /backend-challenge/jar/
RUN find /backend-challenge/ -iname '*.jar' -exec cp {} /backend-challenge/jar/ \;

FROM java:8

WORKDIR /backend-challenge

COPY --from=builder /backend-challenge/jar/* /backend-challenge/

ENTRYPOINT ["java", "-jar", "backend-challenge-1.0-SNAPSHOT.jar"]
