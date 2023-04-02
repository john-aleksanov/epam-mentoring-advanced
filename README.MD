# Overview
This branch showcases a very simple Spring Boot 3 Gradle http-based API project secured by Spring Security. It
builds on the http-based project from task 3 of the training.

To run the project, run the main `Application` class in the `jmp-service-rest` module.

The project configures three in-memory users:
1. 'admin' with password 'admin' and role 'VIEW_ADMIN'
2. 'user' with password 'user' and role 'VIEW_INFO'
3. 'superadmin' with password 'admin' and roles 'VIEW_INFO', 'VIEW_ADMIN'

There are three endpoints of interest:
1. `/info` - requires the 'VIEW_INFO' role
2. `/admin` - requires the 'VIEW_ADMIN' role
3. `/swagger-ui/index.html` - is left unauthenticated and can be accessed without first logging in.

# Notes
1. I didn't have enough time with my vacation to complete all the tasks in the code, so I will just provide solutions here.
2. Custom login and logout pages are configured through `SecurityFilterChain` like this:
```java
http
    .and().formLogin()
        .loginPage("/login") // Custom login URL
        .permitAll()
    .and().logout()
        .logoutUrl("/logout") // Custom logout URL
        .logoutSuccessUrl("/login?logout") // Redirect URL after successful logout
        .permitAll()
    .and()
    ...
```
3. Implementing blocking user email for five minutes after three unsuccessful attempts is done as follows:
    1. Implement a service that would track login attempts and the cooldown period. Can be implemented as a ThreadLocal
cache or a cache with 5 mins expiration. If we want it to survive application restarts, we will need persistence.
    2. Implement a custom filter that delegates checking whether the user is blocked or not to the service and either
returns (if blocked) or lets the request down the filter chain.
    3. The second Spring Boot app is straightforward: an HTML page with a form that generates a POST request to the backend
to save the entered text and generate a unique secret. Can be implemented as a ConcurrentHashMap of secret to text entered.
Also, provide a POST endpoint (so that we can send the secret in the body not to disclose it in the query string) for retrieving
the information and deleting it from the map atomically).
4. Dockerizing everything is a matter of adding a Spring Boot gradle plugin to the `build.gradle` file to be able to create
a fat jar with all dependencies and then create a `Dockerfile`, roughly with the following content:
```dockerfile
FROM openjdk:17-jre-slim

WORKDIR /app

COPY build/libs/app-0.1.0.jar /app/app.jar

EXPOSE 8080

CMD ["java", "-jar", "app.jar"]
```
and finally creating a `docker-compose.yml` file that would run the two Spring Boot applications and any dependencies (like
DBs for persistence).