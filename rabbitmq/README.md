# Overview

This is a simple example written in Java of how to use the RabbitMQ client library to send and receive messages.

# Usage
1. Run `docker-compose up` to start the RabbitMQ broker. The broker will be available at `localhost:5672`.
    The UI will be available at `http://localhost:15762` (username: `admin`, password: `admin`).
    Docker will mount a volume to persist the data as well as to read two configuration files - `rabbitmq.conf` and `definitions.json`.
    The latter file contains the definitions of the exchanges, queues and bindings that will be created when the broker starts.
    `luke_queue` is configured with a ttl of 10 seconds and a size of 1 message. 
2. Run the producer from IntelliJ to start the producer. It will expose an `POST api/message` endpoint that will send 
    a message to the `notification` exchange. Send any conforming message through the endpoint and observe the message to
    come to `luke_queue` first and go to `luke_dlq` after 10 seconds. Send 2-3 more messages at once and observe the first
    come to `luke_queue` and the rest to `luke_dlq` immediately.

# Notes
This is a very simple example just to showcase the usage of the Java RabbitMQ client library. As such, many shortcuts have been taken
and things have been hardcoded. If this was a production project, I would have used a dependency injection framework like Dagger
(Spring or Guice would be an overkill here). I would have used a logging framework to log the messages instead of printing them to the console.
I would have written a script for the producer and consumer to check for the broker availability before starting so that the project
could have been started not service by service and through IntelliJ, but with a simple `docker-compose up`.
Also, unit tests for the producer and consumer would cover the entire code base.
