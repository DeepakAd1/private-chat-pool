package com.chat.privatepool.repository;

import com.chat.privatepool.constants.JoinPolicy;
import com.chat.privatepool.object.Topic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TopicDao extends JpaRepository<Topic, Long> {

    List<Topic> findAllByIsActiveTrueAndJoinPolicy(JoinPolicy joinPolicy);

}
