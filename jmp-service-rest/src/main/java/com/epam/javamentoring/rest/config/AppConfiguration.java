package com.epam.javamentoring.rest.config;

import com.epam.javamentoring.rest.subscription.CloudSubscriptionService;
import com.epam.javamentoring.rest.subscription.SubscriptionService;
import com.epam.javamentoring.rest.user.CloudUserService;
import com.epam.javamentoring.rest.user.UserService;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import java.util.List;

@Configuration
public class AppConfiguration {

    @Bean
    public UserService userService() {
        return new CloudUserService();
    }

    @Bean
    public SubscriptionService subscriptionService() {
        return new CloudSubscriptionService();
    }

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info().title("Java Mentoring Advanced - Rest")
                        .description("Rest application for the Java Mentoring course")
                        .version("0.0.1"));
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests()
                .requestMatchers("/swagger-ui/**").permitAll()
                .requestMatchers("/v3/**").permitAll()
                .requestMatchers("/info").hasRole("VIEW_INFO")
                .requestMatchers("/admin/**").hasRole("VIEW_ADMIN")
                .anyRequest().authenticated()
                .and().formLogin()
                .and().httpBasic()
                .and().build();
    }
    @Bean
    public InMemoryUserDetailsManager inMemoryUserDetailsManager(PasswordEncoder encoder) {
        var superAdmin = User.builder()
                .username("superadmin")
                .password(encoder.encode("superadmin"))
                .roles("VIEW_ADMIN", "VIEW_INFO")
                .build();
        var admin = User.builder()
                .username("admin")
                .password(encoder.encode("admin"))
                .roles("VIEW_ADMIN")
                .build();
        var user = User.builder()
                .username("user")
                .password(encoder.encode("user"))
                .roles("VIEW_INFO")
                .build();
        return new InMemoryUserDetailsManager(List.of(superAdmin, admin, user));
    }

    @Bean
    public PasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
