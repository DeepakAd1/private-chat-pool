package com.chat.privatepool.service;

import com.chat.privatepool.constants.JoinPolicy;
import com.chat.privatepool.dto.response.CommonResponseObject;
import com.chat.privatepool.object.Topic;
import com.chat.privatepool.repository.TopicDao;
import com.chat.privatepool.util.RequestContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class TopicService {

    private final TopicDao topicDao;

    public CommonResponseObject getTopicList(JoinPolicy type) {
        Long userId = RequestContext.get().getUserId();
        try {
            List<Topic> activeTopicList = topicDao.findAllByIsActiveTrueAndJoinPolicy(type);
            return CommonResponseObject.setData("Data fetched successfully", activeTopicList);
        } catch (Exception e) {
            log.error("Error in getTopicList() for userId {} : {}", userId, e.getMessage(), e);
            return CommonResponseObject.setErrorMessage(e.getMessage());
        }
    }
}
