package com.epam.javamentoring.rest.subscription.dto;

import com.epam.javamentoring.rest.user.dto.User;

import java.time.LocalDate;

public record Subscription(
		Long id,
		User user,
		LocalDate startDate) {
}
