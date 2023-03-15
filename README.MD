# Overview
This branch showcases a trivial Spring Boot project with two <code>DataSource</code> beans that are configured manually
and are environment-specific.
To achieve this, we  firstly add an h2 dependency to our project. We then add a <code>@Configuration</code> annotation
to the main Java class so that Spring Boot picks this class for bean definition scanning and add two <code>@Bean</code>-
annotated methods where we define the two custom data sources. Each of the methods is then annotated with a <code>
@Profile</code> annotation to distinguish between two environments.
</br>
</br>
To check that everything works as expected, we provide a <code>@SpringBootTest</code> class that introduces two tests.
The first test only tests that the context loads and that the <code>DataSource</code> bean that we have in the context
is not null. The second test creates a table in the in-memory H2 database, writes one record to it and then retrieves
the record by id. We verify that the record exists. We also verify that the <code>DataSource</code> bean in the Spring
application context is named correctly in line with the environment that we run the test in.