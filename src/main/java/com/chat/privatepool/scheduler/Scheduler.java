package com.chat.privatepool.scheduler;

import com.chat.privatepool.util.SessionRegistry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.PingMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.Set;

@Slf4j
@Component
public class Scheduler {

    @Scheduled(fixedRate = 30000)
    public void ping() throws IOException {
        try {
            Set<WebSocketSession> sessions = SessionRegistry.getAll(1L);
            for (WebSocketSession session : sessions) {
                if (session.isOpen()) {
                    session.sendMessage(new PingMessage());
                }
            }
        } catch (Exception e) {
            log.error("Error in scheduler of ping {}", e.getMessage(), e);
        }
    }

}
