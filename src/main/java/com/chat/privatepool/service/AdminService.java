package com.chat.privatepool.service;

import com.chat.privatepool.constants.SubscriptionStatus;
import com.chat.privatepool.dto.request.RequestDto;
import com.chat.privatepool.dto.response.CommonResponseObject;
import com.chat.privatepool.object.Subscriber;
import com.chat.privatepool.repository.SubscriberRepo;
import com.chat.privatepool.util.CommonUtil;
import com.chat.privatepool.util.RequestContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdminService {

    private final SubscriberRepo subscriberRepo;

    public CommonResponseObject getPendingRequests(long topicId) {
        Long userId = RequestContext.get().getUserId();
        try {
            List<Subscriber> subscriberList = subscriberRepo.findAllByIdAndStatus(topicId, SubscriptionStatus.PENDING);
            return CommonResponseObject.setData("Data fetched successfully!", subscriberList);
        } catch (Exception e) {
            log.error("Error in getPendingRequests() for userId {}", userId, e);
            return CommonResponseObject.setErrorMessage(e.getMessage());
        }
    }

    public CommonResponseObject adminApproval(RequestDto requestDto) {
        Long userId = RequestContext.get().getUserId();
        try {
            if (requestDto == null || requestDto.getUserId() == null || requestDto.getTopicId() == null) {
                return CommonResponseObject.setErrorMessage("PayLoad is mandatory!");
            }
            if (!CommonUtil.isAdmin(userId, requestDto.getTopicId())) {
                log.warn("The logged-in user {} is not an admin inside adminApproval()", userId);
                return CommonResponseObject.setErrorMessage("The logged-in user is not an admin!");
            }
            if (subscriberRepo.updateSubscriptionStatus(requestDto.getUserId(), requestDto.getTopicId(), requestDto.getSubscriptionStatus()) > 0) {
                return CommonResponseObject.setData("Updated successfully!", null);
            }
            return CommonResponseObject.setErrorMessage("Not updated!");
        } catch (Exception e) {
            log.error("Error in adminApproval() {}", e.getMessage(), e);
            return CommonResponseObject.setErrorMessage(e.getMessage());
        }
    }
}
