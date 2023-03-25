package com.epam.javamentoring.rest.user.dto;

public record UserRequestDto(
		Long id,
		String name,
		String lastName,
		String birthday) {
}
