package com.epam.javamentoring;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ActiveProfiles;

import javax.sql.DataSource;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("DEV")
class SpringFoundationTest {

	@Autowired
	private ApplicationContext ctx;

	@Autowired
	private DataSource dataSource;

	@Test
	void contextLoads() {
		assertThat(dataSource).isNotNull();
	}

	@Test
	@DisplayName("When we create a table in the autoconfigured in-memory H2 datasource and insert a record there, " +
			"than we can read it correctly")
	void whenInsertRecordIntoInMemoryDatasourceThenRecordInTable() throws Exception {
		// GIVEN
		var con = dataSource.getConnection();
		var statement = con.createStatement();

		// WHEN
		var recordResult = statement.executeQuery("SELECT text FROM temp WHERE id = 1");
		var dataSourceBeans = ctx.getBeansOfType(DataSource.class);

		// THEN
		assertThat(recordResult.next()).isTrue();
		assertThat(dataSourceBeans.containsKey("devDataSource")).isTrue();
	}

}