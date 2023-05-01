package com.epam.messaging.consumer;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.Session;

public class ConsumerApplication {
    public static void main(String[] args) {
        var factory = new ActiveMQConnectionFactory("tcp://broker:61616");
        try (var connection = getConnection(factory);
             var session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
             var consumer = session.createConsumer(session.createQueue("HELLO_QUEUE"))) {
            var message = consumer.receive();
            System.out.println(message);
        } catch (JMSException e) {
            throw new RuntimeException(e);
        }
    }

    private static Connection getConnection(ActiveMQConnectionFactory factory) throws JMSException {
        var connection = factory.createConnection();
        connection.start();
        return connection;
    }
}