package com.chat.privatepool.strategy;

import com.chat.privatepool.constants.SubscriptionStatus;
import com.chat.privatepool.dto.request.GenericRequestDto;
import com.chat.privatepool.dto.request.TopicRequestDto;
import com.chat.privatepool.dto.response.CommonResponseObject;
import com.chat.privatepool.object.Subscriber;
import com.chat.privatepool.object.Topic;
import com.chat.privatepool.repository.SubscriberRepo;
import com.chat.privatepool.repository.TopicDao;
import com.chat.privatepool.util.CommonUtil;
import com.chat.privatepool.util.RequestContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Slf4j
@Component
@RequiredArgsConstructor
public class TopicStrategy implements GenericCrudOps<CommonResponseObject> {

    private final TopicDao topicDao;
    private final SubscriberRepo subscriberRepo;

    @Override
    public CommonResponseObject save(GenericRequestDto requestDto) {
        CommonResponseObject commonResponseObject = new CommonResponseObject();
        try {
            if (!(requestDto instanceof TopicRequestDto topicRequestDto)) {
                CommonResponseObject.setErrorMessage(commonResponseObject, "Different instance!!");
                return commonResponseObject;
            }
            Topic topic = createTopicFromDto(topicRequestDto);
            topic = topicDao.save(topic);
            subscriberRepo.save(createSubscriber(topic));
            CommonResponseObject.setData(commonResponseObject, "saved successfully", topic);
            return commonResponseObject;
        } catch (Exception e) {
            log.error("Error in saving Topic!!", e);
            CommonResponseObject.setErrorMessage(commonResponseObject, e.getMessage());
            return commonResponseObject;
        }
    }

    private Subscriber createSubscriber(Topic topic) {
        Subscriber subscriber = new Subscriber();
        subscriber.setTopicId(topic.getId());
        subscriber.setIsPremium(false);
        subscriber.setIsAdmin(true);
        subscriber.setUserId(topic.getCreatedBy());
        subscriber.setStatus(SubscriptionStatus.ACTIVE);
        subscriber.setRemovedBy(0L);
        subscriber.setJoinedAt(CommonUtil.getCurrentTime());
        return subscriber;
    }

    private static @NonNull Topic createTopicFromDto(TopicRequestDto topicRequestDto) {
        Topic topic = new Topic();
        topic.setDescription(topicRequestDto.getDescription());
        topic.setName(topicRequestDto.getName());
        topic.setCreatedByName(CommonUtil.isEmptyStr(topicRequestDto.getCreatedByName()) ? RequestContext.get().getDisplayName() : topicRequestDto.getCreatedByName());
        topic.setVisibility(topicRequestDto.getVisibility());
        topic.setIsActive(topicRequestDto.getIsActive());
        topic.setCloseAt(topicRequestDto.getCloseAt());
        topic.setCreatedAt(LocalDateTime.now());
        topic.setIsPremium(topicRequestDto.getIsPremium());
        topic.setJoinPolicy(topicRequestDto.getJoinPolicy());
        topic.setCreatedBy(RequestContext.get().getUserId());
        return topic;
    }

    @Override
    public CommonResponseObject update(GenericRequestDto requestDto) {
        CommonResponseObject commonResponseObject = new CommonResponseObject();
        try {
            if (!(requestDto instanceof TopicRequestDto topicRequestDto)) {
                CommonResponseObject.setErrorMessage(commonResponseObject, "Different instance!!");
                return commonResponseObject;
            }
            //update logic
            Topic topic = updateFromDto(topicRequestDto);
            if (topic == null) return CommonResponseObject.setErrorMessage("Invalid payload!");
            topicDao.save(topic);
            return CommonResponseObject.setData("Data Updated Successfully!", topic);
        } catch (Exception e) {
            log.error("Error in update topic!!", e);
            return CommonResponseObject.setErrorMessage(commonResponseObject, e.getMessage());
        }
    }

    private Topic updateFromDto(TopicRequestDto requestDto) {
        if (requestDto.getId() <= 0) {
            return null;
        }
        Topic topic = topicDao.findById((long) requestDto.getId()).orElse(null);
        if (topic == null) return null;

        Topic newTopic = new Topic();
        newTopic.setCreatedBy(requestDto.getCreatedBy() != null ? requestDto.getCreatedBy() : topic.getCreatedBy());
        newTopic.setName(requestDto.getName() != null ? requestDto.getName() : topic.getName());
        newTopic.setVisibility(requestDto.getVisibility() != null ? requestDto.getVisibility() : topic.getVisibility());
        newTopic.setDescription(requestDto.getDescription() != null ? requestDto.getDescription() : topic.getDescription());
        newTopic.setJoinPolicy(requestDto.getJoinPolicy() != null ? requestDto.getJoinPolicy() : topic.getJoinPolicy());
        newTopic.setIsPremium(requestDto.getIsPremium() != null ? requestDto.getIsPremium() : topic.getIsPremium());
        newTopic.setIsActive(requestDto.getIsActive() != null ? requestDto.getIsActive() : topic.getIsActive());
        newTopic.setCreatedByName(requestDto.getCreatedByName() != null ? requestDto.getCreatedByName() : topic.getCreatedByName());
        newTopic.setCloseAt(requestDto.getCloseAt() != null ? requestDto.getCloseAt() : topic.getCloseAt());
        return newTopic;
    }

    @Override
    public CommonResponseObject remove(GenericRequestDto requestDto) {
        CommonResponseObject commonResponseObject = new CommonResponseObject();
        try {
            if (!(requestDto instanceof TopicRequestDto topicRequestDto)) {
                CommonResponseObject.setErrorMessage(commonResponseObject, "Different instance!!");
                return commonResponseObject;
            }
            //remove logic
            if (topicRequestDto.getId() <= 0) {
                return CommonResponseObject.setErrorMessage("Invalid Id" + topicRequestDto.getId());
            }
            Topic topic = topicDao.findById((long) topicRequestDto.getId()).orElse(null);
            if (topic == null) {
                return CommonResponseObject.setErrorMessage("No Data Found");
            }
            topic.setIsActive(false);
            topicDao.save(topic);
            return CommonResponseObject.setData("Topic deleted successfully!", topic);
        } catch (Exception e) {
            log.error("Error in update topic!!", e);
            return CommonResponseObject.setErrorMessage(commonResponseObject, e.getMessage());
        }
    }

    @Override
    public CommonResponseObject get(GenericRequestDto requestDto) {
        CommonResponseObject commonResponseObject = new CommonResponseObject();
        try {
            if (!(requestDto instanceof TopicRequestDto topicRequestDto)) {
                CommonResponseObject.setErrorMessage(commonResponseObject, "Different instance!!");
                return commonResponseObject;
            }
            //get logic
            if (topicRequestDto.getId() <= 0) {
                return CommonResponseObject.setErrorMessage("Invalid Id" + topicRequestDto.getId());
            }
            Topic topic = topicDao.findById((long) topicRequestDto.getId()).orElse(null);
            if (topic == null) {
                return CommonResponseObject.setErrorMessage("No Data Found");
            }
            return CommonResponseObject.setData("Data Fetched successfully", topic);
        } catch (Exception e) {
            log.error("Error in update topic!!", e);
            return CommonResponseObject.setErrorMessage(commonResponseObject, e.getMessage());
        }
    }

    @Override
    public CommonResponseObject getAll(GenericRequestDto requestDto) {
        CommonResponseObject commonResponseObject = new CommonResponseObject();
        try {
            if (!(requestDto instanceof TopicRequestDto topicRequestDto)) {
                CommonResponseObject.setErrorMessage(commonResponseObject, "Different instance!!");
                return commonResponseObject;
            }
            //getAll logic
            return commonResponseObject;

        } catch (Exception e) {
            log.error("Error in update topic!!", e);
            return CommonResponseObject.setErrorMessage(commonResponseObject, e.getMessage());
        }
    }

    @Override
    public boolean canHandle(GenericRequestDto requestDto) {
        return requestDto instanceof TopicRequestDto;
    }
}
