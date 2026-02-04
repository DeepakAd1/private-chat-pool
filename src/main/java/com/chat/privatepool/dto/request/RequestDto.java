package com.chat.privatepool.dto.request;

import com.chat.privatepool.constants.SubscriptionStatus;
import lombok.Data;

@Data
public class RequestDto {
    private Long topicId;
    private Long userId;
    private SubscriptionStatus subscriptionStatus;
}
