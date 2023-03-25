package com.epam.javamentoring.rest.user;

import com.epam.javamentoring.rest.user.dto.User;
import com.epam.javamentoring.rest.user.dto.UserResponseDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;

@Component
public class UserToUserResponseDtoConverter implements Converter<User, UserResponseDto> {

	private final DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE;

	@Override
	public UserResponseDto convert(User source) {
		return new UserResponseDto(
				source.id(),
				source.name(),
				source.lastName(),
				formatter.format(source.birthday()));
	}
}
