package com.epam.javamentoring.rest.user;

import com.epam.javamentoring.rest.user.dto.User;
import com.epam.javamentoring.rest.user.dto.UserRequestDto;
import com.epam.javamentoring.rest.user.dto.UserResponseDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
public class UserRequestDtoToUserConverter implements Converter<UserRequestDto, User> {

	@Override
	public User convert(UserRequestDto source) {
		return new User(
				source.id(),
				source.name(),
				source.lastName(),
				LocalDate.parse(source.birthday()));
	}
}
