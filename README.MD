'# Overview
This branch showcases a very simple Kafka producer and consumer project that uses Avro. The producer and consumer are
bundled into a multi-module Gradle project.
To run the server and test the clients:

1. Run

```shell
docker compose up
```


from the project root. This will spin up three containers: Zookeeper, Kafka broker and Schema registry. If the schema
registry container does not start, this is probably due to the fact that the Kafka broker container has not yet started.
Then start the containers separately:

```shell
docker compose up zookeeper
docker compose up kafka
docker compose up schema-registry
```

2. Build and run the <code>KafkaConsumer</code> class. The Gradle Avro plugin will read the <code>person.avsc</code>
file from the <code>avro</code> directory and generate the respective Java classes. When the consumer is started, you
should see 'Consumer started' in the logs.

3. Build and run the <code>KafkaProducer</code> class. The Gradle Avro plugin will read the same <code>person.avsc</code>
file from the <code>avro</code> directory and generate the respective Java classes. You should be able to see

> Sent message: {"firstName": "Anakin", "lastName": "Skywalker"}

4. After some time, in the consumer logs you should be able to see:

> Received message: key 'Skywalker', value '{"firstName": "Anakin", "lastName": "Skywalker"}'