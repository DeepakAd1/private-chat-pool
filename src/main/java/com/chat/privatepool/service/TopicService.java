package com.chat.privatepool.service;

import com.chat.privatepool.constants.JoinPolicy;
import com.chat.privatepool.constants.SubscriptionStatus;
import com.chat.privatepool.dto.response.CommonResponseObject;
import com.chat.privatepool.object.Message;
import com.chat.privatepool.object.Subscriber;
import com.chat.privatepool.object.Topic;
import com.chat.privatepool.object.User;
import com.chat.privatepool.repository.MessageDao;
import com.chat.privatepool.repository.SubscriberRepo;
import com.chat.privatepool.repository.TopicDao;
import com.chat.privatepool.repository.UserRepository;
import com.chat.privatepool.util.CommonUtil;
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
    private final UserRepository userRepository;
    private final SubscriberRepo subscriberRepo;
    private final MessageDao messageDao;

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

    public CommonResponseObject joinRequest(long topicId, boolean isPremium) {
        Long userId = RequestContext.get().getUserId();
        try {
            Topic topic = topicDao.findById(topicId).orElse(null);
            if (topic == null) {
                log.info("No topic Found with the id {} inside joinRequest() ", topicId);
                return CommonResponseObject.setErrorMessage("No topic found");
            }
            requestBasedOnJoinPolicy(topic, userId, isPremium);
            return CommonResponseObject.setData("Requested successfully", null);
        } catch (Exception e) {
            log.error("Error in joinRequest() for userId {} , topicId {}", userId, topicId);
            return CommonResponseObject.setErrorMessage(e.getMessage());
        }
    }

    private void requestBasedOnJoinPolicy(Topic topic, Long userId, boolean isPremium) {
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            log.warn("The user {} is not found inside requestBasedOnJoinPolicy()", userId);
            return;
        }
        Subscriber subscriber = userToSubscriber(user);
        subscriber.setTopicId(topic.getId());
        subscriber.setIsPremium(isPremium);
        switch (topic.getJoinPolicy()) {
            case PUBLIC -> {
                subscriber.setStatus(SubscriptionStatus.ACTIVE);
            }
            case REQUEST_ONLY -> {
                subscriber.setStatus(SubscriptionStatus.PENDING);
            }
        }
        subscriberRepo.save(subscriber);
    }

    private Subscriber userToSubscriber(User user) {
        Subscriber subscriber = new Subscriber();
        subscriber.setUserId(user.getId());
        subscriber.setJoinedAt(CommonUtil.getCurrentTime());
        subscriber.setRemovedBy(0L);
        return subscriber;
    }
}
