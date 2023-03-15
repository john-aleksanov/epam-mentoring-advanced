package com.epam.javamentoring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
@Endpoint(id = "custom")
@Configuration
public class SpringFoundation {
	public static void main(String[] args) {
		SpringApplication.run(SpringFoundation.class, args);
	}

	@GetMapping("test")
	public String test() {
		return "test";
	}

	@ReadOperation
	public String sayHello() {
		return "Hello from a custom actuator endpoint!";
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		return http.authorizeHttpRequests()
				.anyRequest().permitAll()
				.and().build();
	}
}
