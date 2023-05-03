# Overview

This is a simple example written in Java of how to use the [ActiveMQ](http://activemq.apache.org/) message broker.

# Usage
1. Run `docker-compose up broker` to start the ActiveMQ broker. The broker will be available at `tcp://localhost:61616`.
    The UI will be available at `http://localhost:8161/admin/` (username: `admin`, password: `admin`).
2. Run `docker-compose up producer` to start the producer. The producer will send a single message to the broker and quit.
3. Run `docker-compose up consumer` to start the consumer. The consumer will receive the message, print it to the console and quit.

# Notes
The interaction goes through a topic, and the subscriber is non-durable, meaning that if the subscriber is not running when the message is sent,
the message will be lost. If the subscriber is running when the message is sent, the message will be received by the subscriber.
If the subscriber is running and the message is sent before the subscriber has subscribed to the topic, the message will be lost.
So you'll have to run the subscriber first and then the producer.

This is a very simple example just to showcase the usage of the Java ActiveMQ client library. As such, many shortcuts have been taken
and things have been hardcoded. For example, the broker URL is hardcoded in the producer and consumer, as well as the queue name is hardcoded.
If this was a production project, these would have been configurable. I would also have used a dependency injection framework like Dagger
(Spring or Guice would be an overkill here). I would have used a logging framework to log the messages instead of printing them to the console.
I would have written a script for the producer and consumer to check for the broker availability before starting so that the project
could have been started not service by service, but with a simple `docker-compose up`. Also, unit tests for the producer and consumer
would cover the entire code base.