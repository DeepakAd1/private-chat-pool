package com.chat.privatepool.strategy;

import com.chat.privatepool.dto.request.GenericRequestDto;
import com.chat.privatepool.dto.request.TopicRequestDto;
import com.chat.privatepool.dto.response.CommonResponseObject;
import com.chat.privatepool.object.Topic;
import com.chat.privatepool.repository.TopicDao;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

import java.time.LocalDateTime;

@Slf4j
@Component
@RequiredArgsConstructor
public class TopicStrategy implements GenericCrudOps<CommonResponseObject> {

    private final TopicDao topicDao;
    private final ObjectMapper objectMapper;

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
            CommonResponseObject.setData(commonResponseObject, "saved successfully", topic);
            return commonResponseObject;
        } catch (Exception e) {
            log.error("Error in saving Topic!!", e);
            CommonResponseObject.setErrorMessage(commonResponseObject, e.getMessage());
            return commonResponseObject;
        }
    }

    private static @NonNull Topic createTopicFromDto(TopicRequestDto topicRequestDto) {
        Topic topic = new Topic();
        topic.setDescription(topicRequestDto.getDescription());
        topic.setName(topicRequestDto.getName());
        topic.setCreatedByName(topicRequestDto.getCreatedByName());
        topic.setVisibility(topicRequestDto.getVisibility());
        topic.setIsActive(topicRequestDto.getIsActive());
        topic.setCloseAt(topicRequestDto.getCloseAt());
        topic.setCreatedAt(LocalDateTime.now());
        topic.setIsPremium(topicRequestDto.getIsPremium());
        topic.setJoinPolicy(topicRequestDto.getJoinPolicy());
        topic.setCreatedBy(topicRequestDto.getCreatedBy());
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
            return commonResponseObject;

        } catch (Exception e) {
            log.error("Error in update topic!!", e);
            return CommonResponseObject.setErrorMessage(commonResponseObject, e.getMessage());
        }
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
            return commonResponseObject;

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
            return commonResponseObject;

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
