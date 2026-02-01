package com.chat.privatepool.service;

import com.chat.privatepool.constants.MessageCategory;
import com.chat.privatepool.dto.request.MessageRequestDto;
import com.chat.privatepool.constants.TopicUnreadView;
import com.chat.privatepool.constants.WebSocketPayload;
import com.chat.privatepool.strategy.MessageStrategy;
import com.chat.privatepool.strategy.SubscriberStrategy;
import com.chat.privatepool.strategy.TopicStrategy;
import com.chat.privatepool.util.CommonUtil;
import com.chat.privatepool.util.SessionRegistry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
@Component
@RequiredArgsConstructor
public class ChatWebSocketHandler extends TextWebSocketHandler {

    private final ObjectMapper objectMapper;
    private final TopicStrategy topicStrategy;
    private final MessageStrategy messageStrategy;
    private final SubscriberStrategy subscriberStrategy;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        // after user connected
        try {
            Long userId = (Long) session.getAttributes().get("userId");
            SessionRegistry.add(userId, session);

            if (CommonUtil.isNonPrimitiveEmpty(userId)) return;
            Set<Long> usersTopicId = subscriberStrategy.getTopicListByUserId(userId);

            if (CollectionUtils.isEmpty(usersTopicId)) {
                log.info("No topics found for the userId {}", userId);
                return;
            }

            List<TopicUnreadView> unreadViews =
                    subscriberStrategy.getUnreadCounts(userId);

            if (unreadViews.isEmpty()) return;
            WebSocketPayload payload = new WebSocketPayload(
                    MessageCategory.TOPIC_UNREAD_SUMMARY.getVal(),
                    unreadViews
            );

            session.sendMessage(
                    new TextMessage(objectMapper.writeValueAsString(payload))
            );
        } catch (IOException e) {
            log.error("Error in afterConnectionEstablished() {} ", e.getMessage(), e);
        }
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) {
        // user sent message
        try {
            Long userId = (Long) session.getAttributes().get("userId");
            String originalPayload = message.getPayload();
            MessageRequestDto body = objectMapper.convertValue(originalPayload, MessageRequestDto.class);
            Long topicId = body.getTopicId();
            if (CommonUtil.isNonPrimitiveEmpty(topicId)) return;
            messageStrategy.save(body);
            Set<Long> receiversId = subscriberStrategy.getSubscribersId(topicId);

            WebSocketPayload payload = new WebSocketPayload(MessageCategory.NEW_MESSAGE.getVal(), body);
            String messageToSend = objectMapper.writeValueAsString(payload);

            receiversId.remove(userId);
            Set<Long> offlineUsers = new HashSet<>();
            Set<Long> failedIdsToReceiveMsg = new HashSet<>();
            receiversId.forEach(receiver -> {
                Set<WebSocketSession> availableUsers = SessionRegistry.getAll(receiver);
                if (availableUsers.isEmpty()) {
                    offlineUsers.add(receiver);
                }
                for (WebSocketSession ws : availableUsers) {
                    if (ws.isOpen()) {
                        try {
                            ws.sendMessage(new TextMessage(messageToSend));
                        } catch (IOException e) {
                            failedIdsToReceiveMsg.add(receiver);
                            log.error("Error in sending message to userId {} , topicId {}", receiver, topicId);
                        }
                    }
                }
            });
        } catch (Exception e) {
            log.error("Error in handleTextMessage() {}", e.getMessage(), e);
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        // after user disconnected
        Long userId = (Long) session.getAttributes().get("userId");
        SessionRegistry.remove(userId, session);
    }
}
