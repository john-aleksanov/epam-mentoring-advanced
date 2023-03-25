package com.epam.javamentoring.rest.subscription.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.hateoas.RepresentationModel;

@Getter
@AllArgsConstructor
public class SubscriptionResponseDto extends RepresentationModel<SubscriptionResponseDto> {

    private SubscriptionResponseDto(SubscriptionResponseDto source) {
        super(source.getLinks());
        this.id = source.getId();
        this.userId = source.getUserId();
        this.startDate = source.getStartDate();
    }

    private Long id;
    private Long userId;
    private String startDate;

    public static SubscriptionResponseDto from(SubscriptionResponseDto source) {
        return new SubscriptionResponseDto(source);
    }
}
