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
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.server.SecurityWebFilterChain;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
