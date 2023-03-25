package com.epam.javamentoring.rest.subscription.dto;

public record SubscriptionRequestDto(
		Long id,
		Long userId,
		String startDate) {
}
