package com.chat.privatepool.repository;

import com.chat.privatepool.constants.TopicUnreadView;
import com.chat.privatepool.object.Subscriber;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface SubscriberRepo extends JpaRepository<Subscriber, Long> {

    @Query(value = "select user_id from subscribers where topic_id = :topicId and removed_by <= 0", nativeQuery = true)
    public Set<Long> getSubscriberListByTopicId(Long topicId);

    @Query(value = "select topic_id from subscribers where user_id = :userId and removed_by <= 0", nativeQuery = true)
    public Set<Long> getTopicListByUserId(Long userId);

    @Query(value = """
            SELECT 
                s.topic_id AS topicId,
                COUNT(m.id) AS unreadCount
            FROM subscribers s
            LEFT JOIN topic_read_state trs
                ON trs.user_id = s.user_id AND trs.topic_id = s.topic_id
            LEFT JOIN messages m
                ON m.topic_id = s.topic_id
               AND m.id > COALESCE(trs.last_read_message_id, 0)
            WHERE s.user_id = :userId
              AND s.removed_by <= 0
            GROUP BY s.topic_id
            """, nativeQuery = true)
    List<TopicUnreadView> getUnreadCountByUser(Long userId);

}
