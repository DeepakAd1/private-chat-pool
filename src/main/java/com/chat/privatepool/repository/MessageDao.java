package com.chat.privatepool.repository;

import com.chat.privatepool.object.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageDao extends JpaRepository<Message,Long> {
}
