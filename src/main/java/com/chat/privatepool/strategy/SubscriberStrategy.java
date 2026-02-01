package com.chat.privatepool.strategy;

import com.chat.privatepool.dto.request.GenericRequestDto;
import com.chat.privatepool.dto.response.CommonResponseObject;
import com.chat.privatepool.constants.TopicUnreadView;
import com.chat.privatepool.repository.SubscriberRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Slf4j
@Component
@RequiredArgsConstructor
public class SubscriberStrategy implements GenericCrudOps<CommonResponseObject> {

    private final SubscriberRepo subscriberRepo;

    @Override
    public CommonResponseObject save(GenericRequestDto requestDto) {
        return null;
    }

    @Override
    public CommonResponseObject update(GenericRequestDto requestDto) {
        return null;
    }

    @Override
    public CommonResponseObject remove(GenericRequestDto requestDto) {
        return null;
    }

    @Override
    public CommonResponseObject get(GenericRequestDto requestDto) {
        return null;
    }

    @Override
    public CommonResponseObject getAll(GenericRequestDto requestDto) {
        return null;
    }

    @Override
    public boolean canHandle(GenericRequestDto requestDto) {
        return false;
    }

    public Set<Long> getSubscribersId(Long topicId) {
        return subscriberRepo.getSubscriberListByTopicId(topicId);
    }

    public Set<Long> getTopicListByUserId(Long userId) {
        return subscriberRepo.getTopicListByUserId(userId);
    }

    public List<TopicUnreadView> getUnreadCounts(Long userId) {
        return subscriberRepo.getUnreadCountByUser(userId);
    }
}
