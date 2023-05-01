FROM gradle:8-jdk17

WORKDIR /consumer
COPY build.gradle .
COPY src src/
RUN gradle shadowJar

CMD ["java", "-jar", "./build/libs/activemq-consumer.jar"]
