FROM openjdk:11-jre-slim

EXPOSE 8081

WORKDIR /app

COPY build/libs/simplebanking-0.0.1-SNAPSHOT.jar simplebanking-0.0.1-SNAPSHOT.jar

CMD ["java", "-jar", "simplebanking-0.0.1-SNAPSHOT.jar"]