package com.epam.javamentoring;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class SpringFoundationTest {

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
		statement.executeUpdate("CREATE TABLE temp (id INTEGER, text VARCHAR)");
		statement.executeUpdate("INSERT INTO temp VALUES (1, 'abc')");

		// WHEN
		var result = statement.executeQuery("SELECT text FROM temp WHERE id = 1");

		// THEN
		assertThat(result.next()).isTrue();
	}

}