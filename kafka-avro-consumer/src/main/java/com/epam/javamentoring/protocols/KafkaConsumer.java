package com.epam.javamentoring.protocols;

import com.epam.javamentoring.protocols.model.Person;
import io.confluent.kafka.serializers.KafkaAvroDeserializer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.util.List;
import java.util.Properties;

public class KafkaConsumer {

	public static void main(String[] args) {

		Properties props = new Properties();
		props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "http://127.0.0.1:29092");
		props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
		props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, KafkaAvroDeserializer.class.getName());
		props.put(ConsumerConfig.GROUP_ID_CONFIG, "group-1");
		props.put("schema.registry.url", "http://127.0.0.1:8081");

		try (var consumer = new org.apache.kafka.clients.consumer.KafkaConsumer<String, Person>(props);) {
			consumer.subscribe(List.of("avro-person"));
			System.out.println("Consumer started");
			while (true) {
			var records = consumer.poll(Duration.ofSeconds((1)));
				records.forEach(record -> {
					System.out.printf("Received message: key '%s', value '%s'%n", record.key(), record.value());
				});
			}
		}
	}
}
