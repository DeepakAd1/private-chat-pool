package com.chat.privatepool.dto.request;

import com.chat.privatepool.constants.MessageType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class MessageRequestDto extends GenericRequestDto {
    private Long id;
    private Long senderId;
    private String senderName;
    private Long topicId;
    private String content;
    private MessageType messageType;
    private Long replyId = 0L;
    private Boolean isPersisted = true;
    private Boolean isRemoved = false;
}
