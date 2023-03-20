package com.epam.javamentoring.protocols;

import com.epam.javamentoring.protocols.model.Person;
import io.confluent.kafka.serializers.KafkaAvroSerializer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.time.Duration;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class KafkaProducer {

	public static void main(String[] args) {

		Properties props = new Properties();
		props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "http://127.0.0.1:29092");
		props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
		props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, KafkaAvroSerializer.class.getName());
		props.put("schema.registry.url", "http://localhost:8081");

		try (var producer = new org.apache.kafka.clients.producer.KafkaProducer<String, Person>(props);) {
			Person person = new Person();
			person.setFirstName("Anakin");
			person.setLastName("Skywalker");
			var personRecord = new ProducerRecord<>("avro-person", person.getLastName(), person);
			producer.send(personRecord);
			System.out.println("Sent message: " + person);
		}
	}
}
