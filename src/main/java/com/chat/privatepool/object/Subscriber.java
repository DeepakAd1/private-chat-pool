package com.chat.privatepool.object;

import com.chat.privatepool.constants.SubscriptionStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "subscribers",
        uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "topic_id"})
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Subscriber {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "topic_id", nullable = false)
    private Long topicId;

    @Column(name = "is_premium")
    private Boolean isPremium = false;

    @Column(name = "is_admin")
    private Boolean isAdmin = false;

    @Enumerated(EnumType.STRING)
    private SubscriptionStatus status;

    @Column(name = "joined_at")
    private LocalDateTime joinedAt;

    @Column(name = "close_at")
    private LocalDateTime closeAt;

    @Column(name = "removed_by")
    private Long removedBy;

    @Column(name = "removed_reason")
    private String removedReason;

    @PrePersist
    void onJoin() {
        joinedAt = LocalDateTime.now();
    }
}
