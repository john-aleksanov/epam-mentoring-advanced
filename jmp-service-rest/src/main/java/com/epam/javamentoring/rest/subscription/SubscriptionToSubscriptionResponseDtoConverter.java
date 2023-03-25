package com.epam.javamentoring.rest.subscription;

import com.epam.javamentoring.rest.subscription.dto.Subscription;
import com.epam.javamentoring.rest.subscription.dto.SubscriptionResponseDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;


@Component
public class SubscriptionToSubscriptionResponseDtoConverter implements Converter<Subscription,
		SubscriptionResponseDto> {

	private final DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE;

	@Override
	public SubscriptionResponseDto convert(Subscription source) {
		return new SubscriptionResponseDto(
				source.id(),
				source.user().id(),
				formatter.format(source.startDate()));
	}
}
