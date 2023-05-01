FROM gradle:8-jdk17

WORKDIR /producer
COPY build.gradle .
COPY src src/
RUN gradle shadowJar

CMD ["java", "-jar", "./build/libs/activemq-producer.jar"]