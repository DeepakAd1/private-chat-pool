package com.chat.privatepool.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.server.WebSession;
import org.springframework.web.socket.WebSocketSession;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Predicate;

@Slf4j
@Component
public class SessionRegistry {
    private static final Map<Long, Set<WebSocketSession>> userIdToSessions = new ConcurrentHashMap<>();

    public static void add(Long userId, WebSocketSession webSocketSession) {
        userIdToSessions
                .computeIfAbsent(userId, k -> ConcurrentHashMap.newKeySet())
                .add(webSocketSession);
    }

    public static void remove(Long userId, WebSocketSession webSocketSession) {
        Set<WebSocketSession> sessions = userIdToSessions.get(userId);
        if (!CollectionUtils.isEmpty(sessions)) {
            sessions.remove(webSocketSession);
            if (sessions.isEmpty()) {
                userIdToSessions.remove(userId);
            }
        }
    }

    public static Set<WebSocketSession> getAll(Long userId) {
        return userIdToSessions.getOrDefault(userId, new HashSet<>());
    }
}
