package com.epam.javamentoring.rest.subscription;

import com.epam.javamentoring.rest.subscription.dto.Subscription;
import com.epam.javamentoring.rest.subscription.dto.SubscriptionRequestDto;
import com.epam.javamentoring.rest.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class SubscriptionRequestDtoToSubscriptionConverter implements Converter<SubscriptionRequestDto, Subscription> {

	private final UserService userService;

	@Override
	public Subscription convert(SubscriptionRequestDto source) {
		var user = userService.getUser(source.userId());
		return new Subscription(
				source.id(),
				user,
				LocalDate.parse(source.startDate()));
	}
}
