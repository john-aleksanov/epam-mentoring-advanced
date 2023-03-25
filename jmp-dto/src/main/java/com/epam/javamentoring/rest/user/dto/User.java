package com.epam.javamentoring.rest.user.dto;


import java.time.LocalDate;

public record User(
		Long id,
		String name,
		String lastName,
		LocalDate birthday) {

}
