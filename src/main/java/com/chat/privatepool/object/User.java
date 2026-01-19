package com.chat.privatepool.object;

import com.chat.privatepool.constants.UserType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "users",
        indexes = {
                @Index(name = "idx_user_email", columnList = "email"),
                @Index(name = "idx_user_guest_token", columnList = "guest_token")
        }
)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Email is NULL for guest users
     */
    @Column(unique = true)
    private String email;

    /**
     * NULL for guest users
     */
    private String password;

    /**
     * Display name (Anonymous#XXXX or real name)
     * Stored as snapshot for message history
     */
    @Column(nullable = false)
    private String defaultName;

    /**
     * GUEST or REGISTERED
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserType userType;

    /**
     * Used only for guest users
     * Stored in JWT and cookies
     */
    @Column(name = "guest_token", unique = true)
    private String guestToken;

    /**
     * Used for banning / soft delete
     */
    @Column(nullable = false)
    private Boolean isActive = true;

    private LocalDateTime createdAt;

    private LocalDateTime lastLogin;

    /**
     * When guest is converted to registered
     */
    private LocalDateTime upgradedAt;

    @PrePersist
    public void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.lastLogin = LocalDateTime.now();
    }

    @PreUpdate
    public void onUpdate() {
        this.lastLogin = LocalDateTime.now();
    }
}
