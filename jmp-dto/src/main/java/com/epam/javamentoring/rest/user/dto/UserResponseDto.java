package com.epam.javamentoring.rest.user.dto;

public record UserResponseDto(
		Long id,
		String name,
		String lastName,
		String birthday) {
}
