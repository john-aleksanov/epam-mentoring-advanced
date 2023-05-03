# Overview

This is a simple example written in Java of how to use the [ActiveMQ](http://activemq.apache.org/) message broker.

# Usage
1. Run `docker-compose up broker` to start the ActiveMQ broker. The broker will be available at `tcp://localhost:61616`.
    The UI will be available at `http://localhost:8161/admin/` (username: `admin`, password: `admin`).
2. Run `docker-compose up consumer` to start the consumer. The consumer will subscribe to the topic.
3. Quit the consumer.
4. Run `docker-compose up producer` to start the producer. The producer will send a single message to the broker and quit.
5. Run the consumer again. The consumer will receive the message sent by the producer and print it to the console, even
    though it was offline.

# Notes
The interaction goes through a topic, and the subscriber is durable, meaning that it will receive all messages sent to the topic
even if it is offline. This is achieved by setting the `clientId` and `durableSubscriptionName` properties on the `ActiveMQConnectionFactory`.
That said, the consumer will only receive messages sent after it has subscribed to the topic, so we run it first and quit
to subscribe.

This is a very simple example just to showcase the usage of the Java ActiveMQ client library. As such, many shortcuts have been taken
and things have been hardcoded. For example, the broker URL is hardcoded in the producer and consumer, as well as the queue name is hardcoded.
If this was a production project, these would have been configurable. I would also have used a dependency injection framework like Dagger
(Spring or Guice would be an overkill here). I would have used a logging framework to log the messages instead of printing them to the console.
I would have written a script for the producer and consumer to check for the broker availability before starting so that the project
could have been started not service by service, but with a simple `docker-compose up`. Also, unit tests for the producer and consumer
would cover the entire code base.