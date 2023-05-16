# Overview

This is a simple example written in Java of how to use the RabbitMQ client library to send and receive messages.

# Usage
1. Run `docker-compose up` to start the RabbitMQ broker. The broker will be available at `localhost:5672`.
    The UI will be available at `http://localhost:15762` (username: `admin`, password: `admin`).
    Docker will mount a volume to persist the data as well as to read two configuration files - `rabbitmq.conf` and `definitions.json`.
    The latter file contains the definitions of the exchanges, queues and bindings that will be created when the broker starts.
2. Run the consumer from IntelliJ to start the consumer. It will subscribe to the `notification` topic. The consumer is
    configured to receive a message and immediately throw an exception such that retry logic can be tested. The retry logic
    is to send the message to the retry exchange -> retry queue. If, after three processing attempts, the message is still
    not processed successfully, it will be sent to the `failed_messages` queue.
3. Run the producer from IntelliJ to start the producer. It will expose an `POST api/message` endpoint that will send 
    a message to the `notification` exchange. You can send any conforming message through the endpoint. Then, observe the
    consumer console to see the message retry three times and fail. Observe RabbitMQ UI to see the message in the
    `failed_messages` queue.

# Notes
This is a very simple example just to showcase the usage of the Java RabbitMQ client library. As such, many shortcuts have been taken
and things have been hardcoded. If this was a production project, I would have used a dependency injection framework like Dagger
(Spring or Guice would be an overkill here). I would have used a logging framework to log the messages instead of printing them to the console.
I would have written a script for the producer and consumer to check for the broker availability before starting so that the project
could have been started not service by service and through IntelliJ, but with a simple `docker-compose up`.
Also, unit tests for the producer and consumer would cover the entire code base.
