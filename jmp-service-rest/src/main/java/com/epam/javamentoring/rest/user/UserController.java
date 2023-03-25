package com.epam.javamentoring.rest.user;

import com.epam.javamentoring.rest.user.dto.UserRequestDto;
import com.epam.javamentoring.rest.user.dto.UserResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

	private final UserService userService;
	private final UserToUserResponseDtoConverter userToUserResponseDtoConverter;
	private final UserRequestDtoToUserConverter userRequestDtoToUserConverter;

	@GetMapping(path = "/{id}", produces = "application/json")
	@Operation(summary = "Get a user by its ID")
	@ApiResponse(responseCode = "200", description = "The user with the given ID")
	@ApiResponse(responseCode = "404", description = "User with the given ID was not found", content = @Content)
	public UserResponseDto getUser(
			@Parameter(description = "The ID of the user to retrieve") @PathVariable Long id) {
		var user =  userService.getUser(id);
		return userToUserResponseDtoConverter.convert(user);
	}

	@GetMapping(produces = "application/json")
	@Operation(summary = "Get all existing users")
	@ApiResponse(responseCode = "200", description = "A list of all existing users (maybe empty)")
	public Set<UserResponseDto> getAllUsers() {
		Set<UserResponseDto> result = ConcurrentHashMap.newKeySet();
		userService.getAllUsers().parallelStream()
				.map(userToUserResponseDtoConverter::convert)
				.forEach(result::add);
		return result;
	}

	@PostMapping(produces = "application/json")
	@ResponseStatus(code = HttpStatus.CREATED)
	@Operation(summary = "Create a new user")
	@ApiResponse(responseCode = "201", description = "A newly created user")
	public UserResponseDto createUser(
			@Parameter(description = "User to create") @RequestBody UserRequestDto request) {
		var user = userRequestDtoToUserConverter.convert(request);
		var savedUser = userService.createUser(user);
		return userToUserResponseDtoConverter.convert(savedUser);
	}

	@PutMapping(path = "/{id}", produces = "application/json")
	@Operation(summary = "Update an existing user")
	@ApiResponse(responseCode = "200", description = "The updated user")
	public UserResponseDto updateUser(
			@Parameter(description = "The updated user values") @RequestBody UserRequestDto request) {
		var user = userRequestDtoToUserConverter.convert(request);
		var updatedUser = userService.updateUser(user);
		return userToUserResponseDtoConverter.convert(updatedUser);
	}

	@DeleteMapping(path = "/{id}", produces = "application/text")
	@Operation(summary = "Delete a user")
	@ApiResponse(responseCode = "200", description = "The deleted user")
	public UserResponseDto deleteUser(
			@Parameter(description = "The ID of the user to delete") @PathVariable Long id) {
		var deletedUser = userService.deleteUser(id);
		return userToUserResponseDtoConverter.convert(deletedUser);
	}
}
