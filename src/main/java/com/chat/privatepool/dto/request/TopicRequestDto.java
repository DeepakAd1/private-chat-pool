package com.chat.privatepool.dto.request;

import com.chat.privatepool.constants.JoinPolicy;
import com.chat.privatepool.constants.TopicVisibility;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class TopicRequestDto extends GenericRequestDto {
    private int id;
    private String name;
    private String description;
    private Long createdBy;
    private String createdByName;
    private Boolean isPremium = false;
    private TopicVisibility visibility;
    private JoinPolicy joinPolicy;
    private LocalDateTime closeAt;
    private Boolean isActive = true;
}
