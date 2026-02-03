package com.chat.privatepool.repository;

import com.chat.privatepool.object.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageDao extends JpaRepository<Message, Long> {
    List<Message> findAllByTopicIdSortByCreatedAtDesc(Long topicId);

    Page<Message> findAllByTopicIdSortByCreatedAtDesc(Long topicId, Pageable pageable);
}
