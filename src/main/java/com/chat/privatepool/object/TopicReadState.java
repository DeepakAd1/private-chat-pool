package com.chat.privatepool.object;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "topic_read_state")
public class TopicReadState {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    private Long topicId;

    private Long lastReadMessageId;

    private LocalDateTime lastReadAt;
}
