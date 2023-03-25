package com.epam.javamentoring.rest.subscription;

import com.epam.javamentoring.rest.subscription.dto.Subscription;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadLocalRandom;

public class CloudSubscriptionService implements SubscriptionService {

	private final Map<Long, Subscription> subscriptionStore = new ConcurrentHashMap<>();

	@Override
	public Subscription getSubscription(Long id) {
		return Optional.ofNullable(subscriptionStore.get(id))
				.orElseThrow(() -> new NoSuchElementException("Subscription " + id + " not found"));
	}

	@Override
	public Set<Subscription> getAllSubscriptions() {
		return new HashSet<>(subscriptionStore.values());
	}

	@Override
	public Subscription createSubscription(Subscription subscription) {
		var id = ThreadLocalRandom.current().nextLong(1, Long.MAX_VALUE);
		var savedSubscription = new Subscription(id, subscription.user(), subscription.startDate());
		subscriptionStore.put(id, savedSubscription);

		return savedSubscription;
	}

	@Override
	public Subscription updateSubscription(Subscription subscription) {
		var id = subscription.id();
		return subscriptionStore.compute(id, (key, subscriptionToUpdate) -> {
			if (subscriptionToUpdate == null) {
				throw new NoSuchElementException("Subscription " + key + " not found");
			}
			return subscription;
		});
	}

	@Override
	public Subscription deleteSubscription(Long id) {
		return subscriptionStore.remove(id);
	}
}
