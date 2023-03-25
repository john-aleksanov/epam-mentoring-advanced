package com.epam.javamentoring.rest.user;

import com.epam.javamentoring.rest.user.dto.User;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadLocalRandom;

public class CloudUserService implements UserService {

	private final Map<Long, User> userStore = new ConcurrentHashMap<>();

	@Override
	public User getUser(Long id) {
		return Optional.ofNullable(userStore.get(id))
				.orElseThrow(() -> new NoSuchElementException("User " + id + " not found"));
	}

	@Override
	public Set<User> getAllUsers() {
		return new HashSet<>(userStore.values());
	}

	@Override
	public User createUser(User user) {
		var id = ThreadLocalRandom.current().nextLong(1, Long.MAX_VALUE);
		var savedUser = new User(id, user.name(), user.lastName(), user.birthday());
		userStore.put(id, savedUser);

		return savedUser;
	}

	@Override
	public User updateUser(User user) {
		var id = user.id();
		return userStore.compute(id, (key, userToUpdate) -> {
			if (userToUpdate == null) {
				throw new NoSuchElementException("User " + key + " not found");
			}
			return user;
		});
	}

	@Override
	public User deleteUser(Long id) {
		return userStore.remove(id);
	}
}
