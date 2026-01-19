package com.chat.privatepool.repository;

import com.chat.privatepool.object.Topic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TopicDao extends JpaRepository<Topic,Long> {
}
