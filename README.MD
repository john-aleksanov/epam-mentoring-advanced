# Overview
This branch showcases a trivial Spring Boot project with a <code>DataSource</code> bean that is configured manually.
To achieve this, we  firstly add an h2 dependency to our project. We then add a <code>@Configuration</code> annotation
to the main Java class so that Spring Boot picks this class for bean definition scanning and add a <code>@Bean</code>-
annotated method where we define a custom data source.
</br>
</br>
To check that everything works as expected, we provide a <code>@SpringBootTest</code> class that introduces two tests.
The first test only tests that the context loads and that the <code>DataSource</code> bean that we have in the context
is not null. The second test creates a table in the in-memory H2 database, writes one record to it and then retrieves
the record by id. We verify that the record exists. We also verify that the <code>DataSource</code> bean in the Spring
application context is named just as we named it in the bean configuration.