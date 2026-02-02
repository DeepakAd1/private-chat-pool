package com.chat.privatepool.repository;

import com.chat.privatepool.object.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageDao extends JpaRepository<Message, Long> {
    List<Message> findAllByTopicId(Long topicId);
}
