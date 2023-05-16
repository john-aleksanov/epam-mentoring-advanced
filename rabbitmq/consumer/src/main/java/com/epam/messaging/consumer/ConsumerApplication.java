package com.epam.messaging.consumer;

import com.epam.messaging.Notification;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.config.RetryInterceptorBuilder;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.retry.RejectAndDontRequeueRecoverer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.amqp.SimpleRabbitListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.retry.annotation.Retryable;
import org.springframework.retry.interceptor.RetryOperationsInterceptor;

@EnableRetry
@Configuration
@SpringBootApplication
@RequiredArgsConstructor
public class ConsumerApplication {

    private static final String FAILED_EXCHANGE = "failed_notifications";
    private static final String RETRY_EXCHANGE = "retry_exchange";
    private static final String RETRY_ROUTING_KEY = "retry";

    private final RabbitTemplate rabbitTemplate;

    @Retryable
    @RabbitListener(queues = "luke_queue")
    public void listen(Notification notification, Message message) {
        System.out.println("Trying to process notification " + notification);
        try {
            throw new RuntimeException("Exception");
        } catch (Exception e) {
            Integer retryCount = (Integer) message.getMessageProperties().getHeaders().get("x-retry-count");
            if (retryCount == null) {
                System.out.println("Setting retry count to 0");
                retryCount = 0;
            }
            if (retryCount < 3) {
                System.out.println("Retry count is " + retryCount + ", republishing to retry queue.");
                message.getMessageProperties().setHeader("x-retry-count", ++retryCount);
                rabbitTemplate.send(RETRY_EXCHANGE, RETRY_ROUTING_KEY, message);
                System.out.println("Message republished to retry queue. Retry count: " + retryCount);
            } else {
                System.out.println("Could not process notification " + notification + " after 3 retries. Sending to " +
                        "the failed queue.");
                rabbitTemplate.send(FAILED_EXCHANGE, "", message);
            }
        }
    }

    public static void main(String[] args) {
        SpringApplication.run(ConsumerApplication.class, args);
    }

    @Bean
    public RetryOperationsInterceptor retryInterceptor() {
        return RetryInterceptorBuilder.stateless()
                .maxAttempts(3)
                .backOffOptions(1000, 1.0, 3000)
                .recoverer(new RejectAndDontRequeueRecoverer())
                .build();
    }

    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(
            SimpleRabbitListenerContainerFactoryConfigurer configurer,
            CachingConnectionFactory cachingConnectionFactory) {
        var factory = new SimpleRabbitListenerContainerFactory();
        configurer.configure(factory, cachingConnectionFactory);
        factory.setAcknowledgeMode(AcknowledgeMode.AUTO);
        factory.setDefaultRequeueRejected(false);
        factory.setAdviceChain(retryInterceptor());

        return factory;
    }
}


