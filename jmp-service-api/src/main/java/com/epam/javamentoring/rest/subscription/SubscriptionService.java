package com.epam.javamentoring.rest.subscription;

import com.epam.javamentoring.rest.subscription.dto.Subscription;

import java.util.Set;

public interface SubscriptionService {

	Subscription getSubscription(Long id);

	Set<Subscription> getAllSubscriptions();

	Subscription createSubscription(Subscription subscription);

	Subscription updateSubscription(Subscription subscription);

	Subscription deleteSubscription(Long id);
}
