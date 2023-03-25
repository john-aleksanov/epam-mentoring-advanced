package com.epam.javamentoring.rest;

import com.epam.javamentoring.rest.subscription.CloudSubscriptionService;
import com.epam.javamentoring.rest.subscription.SubscriptionService;
import com.epam.javamentoring.rest.user.CloudUserService;
import com.epam.javamentoring.rest.user.UserService;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	public UserService userService() {
		return new CloudUserService();
	}

	@Bean
	public SubscriptionService subscriptionService() {
		return new CloudSubscriptionService();
	}

	@Bean
	public OpenAPI springShopOpenAPI() {
		return new OpenAPI()
				.info(new Info().title("Java Mentoring Advanced - Rest")
						.description("Rest application for the Java Mentoring course")
						.version("v0.0.1"));
	}
}
