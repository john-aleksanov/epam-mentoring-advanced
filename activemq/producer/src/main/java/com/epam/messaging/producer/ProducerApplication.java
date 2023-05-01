package com.epam.messaging.producer;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.Session;

public class ProducerApplication {
    public static void main(String[] args) {
        var factory = new ActiveMQConnectionFactory("tcp://broker:61616");
        try (var connection = getConnection(factory);
             var session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
             var producer = session.createProducer(session.createQueue("HELLO_QUEUE"))) {
            var message = session.createTextMessage("Luke, I'm your father");
            producer.send(message);
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