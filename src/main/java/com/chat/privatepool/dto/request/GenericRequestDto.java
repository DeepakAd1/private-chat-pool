package com.chat.privatepool.dto.request;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Data;

@Data
@JsonTypeInfo(
        property = "type", include = JsonTypeInfo.As.EXISTING_PROPERTY, use = JsonTypeInfo.Id.NAME, visible = true
)
@JsonSubTypes(
        {
                @JsonSubTypes.Type(value = TopicRequestDto.class, name = "Topic"),
                @JsonSubTypes.Type(value = MessageRequestDto.class, name = "Message")
        }
)
public class GenericRequestDto {
    private String type;
}
