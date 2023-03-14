# Overview
This branch showcases a trivial Spring Boot project with a DataSource bean that is autoconfigured by default by Spring Boot.
To achieve this, we simply add a spring-boot-starter-jdbc and h2 dependencies to our project. The starter dependency
provides the <code>EmbeddedDataSourceConfiguration</code> bean, which auto-configures an H2 embedded datasource as it
discovers an H2 jar on the classpath.
</br>
</br>
To check that everything works as expected, we provide a <code>@SpringBootTest</code> class that introduces two tests.
The first test only tests that the context loads and that the <code>DataSource</code> bean that we have in the context
is not null. The second test creates a table in the in-memory H2 database, writes one record to it and then retrieves
the record by id. We verify that the record exists.