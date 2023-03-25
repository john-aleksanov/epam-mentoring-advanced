package com.epam.javamentoring.rest.user;

import com.epam.javamentoring.rest.user.dto.User;

import java.util.Set;

public interface UserService {

	User getUser(Long id);

	Set<User> getAllUsers();

	User createUser(User user);

	User updateUser(User user);

	User deleteUser(Long id);
}
