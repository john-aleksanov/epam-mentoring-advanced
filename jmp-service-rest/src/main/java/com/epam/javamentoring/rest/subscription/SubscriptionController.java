package com.epam.javamentoring.rest.subscription;

import com.epam.javamentoring.rest.subscription.dto.SubscriptionRequestDto;
import com.epam.javamentoring.rest.subscription.dto.SubscriptionResponseDto;
import com.epam.javamentoring.rest.user.UserController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import static java.util.List.of;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.afford;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


@RestController
@RequiredArgsConstructor
@RequestMapping("/subscriptions")
public class SubscriptionController {

    private final SubscriptionService subscriptionService;
    private final SubscriptionToSubscriptionResponseDtoConverter subscriptionToSubscriptionResponseDtoConverter;
    private final SubscriptionRequestDtoToSubscriptionConverter subscriptionRequestDtoToSubscriptionConverter;

    @GetMapping(path = "/{id}", produces = "application/json")
    @Operation(summary = "Get a subscription by its ID")
    @ApiResponse(responseCode = "200", description = "The subscription with the given ID")
    @ApiResponse(responseCode = "404", description = "Subscription with the given ID was not found", content = @Content)
    public SubscriptionResponseDto getSubscription(
            @Parameter(description = "The ID of the subscription to retrieve") @PathVariable Long id) {
        var subscription = subscriptionService.getSubscription(id);
        var response = subscriptionToSubscriptionResponseDtoConverter.convert(subscription);
        var links = List.of(getCreateSubscriptionLink(), getUpdateSubscriptionLink(id), getAllSubscriptionsLink(),
                getSubscriptionByIdLink(id));
        response.add(links);

        return response;
    }

    @GetMapping(produces = "application/json")
    @Operation(summary = "Get all existing subscriptions")
    @ApiResponse(responseCode = "200", description = "A list of all existing subscriptions (maybe empty)")
    public Set<SubscriptionResponseDto> getAllSubscriptions() {
        Set<SubscriptionResponseDto> result = ConcurrentHashMap.newKeySet();
        subscriptionService.getAllSubscriptions().parallelStream()
                .map(subscriptionToSubscriptionResponseDtoConverter::convert)
                .forEach(result::add);
        return result;
    }

    @PostMapping(produces = "application/json")
    @ResponseStatus(code = HttpStatus.CREATED)
    @Operation(summary = "Create a new subscription")
    @ApiResponse(responseCode = "201", description = "A newly created subscription")
    public SubscriptionResponseDto createSubscription(
            @Parameter(description = "Subscription to create") @RequestBody SubscriptionRequestDto request) {
        var subscription = subscriptionRequestDtoToSubscriptionConverter.convert(request);
        var savedSubscription = subscriptionService.createSubscription(subscription);
        return subscriptionToSubscriptionResponseDtoConverter.convert(savedSubscription);
    }

    @PutMapping(path = "/{id}", produces = "application/json")
    @Operation(summary = "Update an existing subscription")
    @ApiResponse(responseCode = "200", description = "The updated subscription")
    public SubscriptionResponseDto updateSubscription(
            @Parameter(description = "Subscription to update") @RequestBody SubscriptionRequestDto request) {
        var subscription = subscriptionRequestDtoToSubscriptionConverter.convert(request);
        var updatedSubscription = subscriptionService.updateSubscription(subscription);
        return subscriptionToSubscriptionResponseDtoConverter.convert(updatedSubscription);
    }

    @DeleteMapping(path = "/{id}", produces = "application/text")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete a subscription")
    @ApiResponse(responseCode = "200", description = "The deleted subscription")
    public SubscriptionResponseDto deleteSubscription(
            @Parameter(description = "The ID of the subscription to delete") @PathVariable Long id) {
        var deletedSubscription = subscriptionService.deleteSubscription(id);
        return subscriptionToSubscriptionResponseDtoConverter.convert(deletedSubscription);
    }

    private Link getAllSubscriptionsLink() {
        return linkTo(methodOn(SubscriptionController.class).getAllSubscriptions()).withRel("all");
    }

    private Link getSubscriptionByIdLink(Long id) {
        return linkTo(methodOn(SubscriptionController.class).getSubscription(id)).withSelfRel();
    }

    private Link getCreateSubscriptionLink() {
        return linkTo(methodOn(SubscriptionController.class).createSubscription(null)).withRel("create");
    }

    private Link getUpdateSubscriptionLink(@Nullable Long id) {
        if (id == null) {
            return linkTo(methodOn(SubscriptionController.class).updateSubscription(null)).withRel("update");
        }
        return linkTo(methodOn(SubscriptionController.class, id).updateSubscription(null)).withRel("update");
    }
}

