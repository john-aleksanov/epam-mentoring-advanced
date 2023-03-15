# Overview
This branch demonstrates a trivial Spring Boot project with a custom actuator endpoint with Spring Security enabled.
To achieve this, we add the actuator and security starters and configure a <code>SecurityFilterChain</code> bean to
permit all http requests to be unauthenticated. We don't configure any authorization for the actuator endpoints al.though
in a real project we would. Finally, we implement a custom actuator endpoint.
</br>
</br>
To verify that everything works as expected, run the project through IntelliJ and send a GET http request to
<pre><code>http://localhost:8080/actuator/custom</code></pre>