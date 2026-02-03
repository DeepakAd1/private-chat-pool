package com.chat.privatepool.service;

import com.chat.privatepool.dto.response.CommonResponseObject;
import com.chat.privatepool.object.Message;
import com.chat.privatepool.repository.MessageDao;
import com.chat.privatepool.util.RequestContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class MessageService {

    private final MessageDao messageDao;

    public CommonResponseObject getMessagesByTopic(long topicId, int page, int limit) {
        Long userId = RequestContext.get().getUserId();
        try {
            Pageable pageable = PageRequest.of(page, limit);
            Page<Message> messages = messageDao.findAllByTopicIdSortByCreatedAtDesc(topicId, pageable);
            List<Message> messageList = messages.getContent();
            return CommonResponseObject.setData("data fetched successfully", messageList);
        } catch (Exception e) {
            log.error("Error in getMessageByTopic() for userId {} : {}", userId, e.getMessage(), e);
            return CommonResponseObject.setErrorMessage(e.getMessage());
        }
    }
}
