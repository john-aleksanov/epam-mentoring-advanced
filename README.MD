# Overview
This branch showcases a very simple Spring Boot 3 Gradle http-based API project with two domain models - `user` and `subscription`.
The project's API is documented through the OpenAPI 3 specification (previously Swagger).
The project is broken down into four Gradle modules:
1. jmp-dto comprises the two domain models and the relating request and response DTOs.
2. jmp-service-api - the project's service layer API.
3. jmp-cloud-service-impl - the API implementation.
4. jmp-service-rest - the controller layer and entry point.

To run the project, run the main `Application` class in the `jmp-service-rest` module.

Swagger is available at
```
http://localhost:8080/swagger-ui/index.html
```

# Notes
1. Again, if this was a production project, I would have done a lot of things differently: logging, SRP including separating
configuration into their own classes, dockerization, DB persistence, etc., but I deemed this unnecessary.
2. Although the task mentioned Swagger 2, I used OpenAPI 3 as the newer specification.
3. Under RFC 7231, a response to a DELETE request might have either 202 Accepted or 204 No content codes but it does not
preclude us from returning the deleted entity which is usually handy to rebuild the UI view, which I did.
4. I implemented persistence as simple maps in the service class with random longs as IDs.
5. I added HATEOAS links to GET /subscriptions/{id} only to save time. Others are done mostly the same way.
6. The division into submodules as is described in the task is arguably suboptimal. We should strive to keep closely related
classes together. As such, I would move the request and response DTOs into the `jmp-service-rest` module as they are coupled
to the controller methods.
7. Even after implementing level 3 of RMM, this API is not RESTful. It's only a prerequisite. More on this [here](https://roy.gbiv.com/untangled/2008/rest-apis-must-be-hypertext-driven).