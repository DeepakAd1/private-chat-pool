package com.chat.privatepool.strategy;

import com.chat.privatepool.dto.request.GenericRequestDto;
import com.chat.privatepool.dto.request.MessageRequestDto;
import com.chat.privatepool.dto.response.CommonResponseObject;
import com.chat.privatepool.object.Message;
import com.chat.privatepool.repository.MessageDao;
import com.chat.privatepool.util.CommonUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

@Slf4j
@Component
@RequiredArgsConstructor
public class MessageStrategy implements GenericCrudOps<CommonResponseObject> {

    private final MessageDao messageDao;
    private final ObjectMapper objectMapper;


    @Override
    public CommonResponseObject save(GenericRequestDto requestDto) {
        CommonResponseObject commonResponseObject = new CommonResponseObject();
        try {
            if (!(requestDto instanceof MessageRequestDto messageRequestDto)) {
                CommonResponseObject.setErrorMessage(commonResponseObject, "Different instance!!");
                return commonResponseObject;
            }
            Message message = createMessage(messageRequestDto);
            message.setCreatedAt(CommonUtil.getCurrentTime());
            message.setModifiedAt(CommonUtil.getCurrentTime());
            messageDao.save(message);
            CommonResponseObject.setData(commonResponseObject, "saved successfully", message);
            return commonResponseObject;
        } catch (Exception e) {
            log.error("Error in saving Message!!", e);
            CommonResponseObject.setErrorMessage(commonResponseObject, e.getMessage());
            return commonResponseObject;
        }
    }

    private Message createMessage(MessageRequestDto messageRequestDto) {
        return objectMapper.convertValue(messageRequestDto, Message.class);
    }

    @Override
    public CommonResponseObject update(GenericRequestDto requestDto) {
        CommonResponseObject commonResponseObject = new CommonResponseObject();
        try {
            if (!(requestDto instanceof MessageRequestDto messageRequestDto)) {
                CommonResponseObject.setErrorMessage(commonResponseObject, "Different instance!!");
                return commonResponseObject;
            }
            //update logic
            Message message = updateFromDto(messageRequestDto);
            if (message == null) return CommonResponseObject.setErrorMessage("Invalid payload!");
            messageDao.save(message);
            return CommonResponseObject.setData("Data Updated Successfully!", message);
        } catch (Exception e) {
            log.error("Error in update Message!!", e);
            return CommonResponseObject.setErrorMessage(commonResponseObject, e.getMessage());
        }
    }

    private Message updateFromDto(MessageRequestDto messageRequestDto) {
        if (messageRequestDto.getId() == null || messageRequestDto.getId() <= 0) {
            return null;
        }
        Message existingMessage = messageDao.findById(messageRequestDto.getId()).orElse(null);
        if (existingMessage == null) {
            log.error("The message with id {} is not found", messageRequestDto.getId());
            return null;
        }

        Message message = new Message();
        message.setId(messageRequestDto.getId());
        message.setContent(CommonUtil.isEmptyStr(messageRequestDto.getContent()) ? existingMessage.getContent() : messageRequestDto.getContent());
        message.setMessageType(messageRequestDto.getMessageType() != null ? messageRequestDto.getMessageType() : existingMessage.getMessageType());
        message.setReplyId(messageRequestDto.getReplyId() != null ? messageRequestDto.getReplyId() : existingMessage.getReplyId());
        message.setSenderName(messageRequestDto.getSenderName() != null ? messageRequestDto.getSenderName() : existingMessage.getSenderName());
        message.setSenderId(CommonUtil.isNonPrimitiveEmpty(messageRequestDto.getSenderId()) ? messageRequestDto.getSenderId() : existingMessage.getSenderId());
        message.setIsPersisted(messageRequestDto.getIsPersisted() != null ? messageRequestDto.getIsPersisted() : existingMessage.getIsPersisted());
        message.setIsRemoved(messageRequestDto.getIsRemoved() != null ? messageRequestDto.getIsRemoved() : existingMessage.getIsRemoved());
        message.setModifiedAt(CommonUtil.getCurrentTime());
        return message;
    }

    @Override
    public CommonResponseObject remove(GenericRequestDto requestDto) {
        CommonResponseObject commonResponseObject = new CommonResponseObject();
        try {
            if (!(requestDto instanceof MessageRequestDto messageRequestDto)) {
                CommonResponseObject.setErrorMessage(commonResponseObject, "Different instance!!");
                return commonResponseObject;
            }
            //update logic
            if (CommonUtil.isNonPrimitiveEmpty(messageRequestDto.getId())) {
                return CommonResponseObject.setErrorMessage("Invalid payload!");
            }
            Message message = messageDao.findById(messageRequestDto.getId()).orElse(null);
            if (message == null) return CommonResponseObject.setErrorMessage("Message not found!");
            message.setIsRemoved(true);
            message.setModifiedAt(CommonUtil.getCurrentTime());
            messageDao.save(message);
            return CommonResponseObject.setData("Message Updated Successfully!", message);
        } catch (Exception e) {
            log.error("Error in Remove Message!!", e);
            return CommonResponseObject.setErrorMessage(commonResponseObject, e.getMessage());
        }
    }

    @Override
    public CommonResponseObject get(GenericRequestDto requestDto) {
        CommonResponseObject commonResponseObject = new CommonResponseObject();
        try {
            if (!(requestDto instanceof MessageRequestDto messageRequestDto)) {
                CommonResponseObject.setErrorMessage(commonResponseObject, "Different instance!!");
                return commonResponseObject;
            }
            //update logic
            if (CommonUtil.isNonPrimitiveEmpty(messageRequestDto.getId())) {
                return CommonResponseObject.setErrorMessage("Invalid payload!");
            }
            Message message = messageDao.findById(messageRequestDto.getId()).orElse(null);
            if (message == null) return CommonResponseObject.setErrorMessage("Message not found!");
            return CommonResponseObject.setData("Message fetched successfully!", message);
        } catch (Exception e) {
            log.error("Error in get Message!!", e);
            return CommonResponseObject.setErrorMessage(commonResponseObject, e.getMessage());
        }
    }

    @Override
    public CommonResponseObject getAll(GenericRequestDto requestDto) {
        return null;
    }

    @Override
    public boolean canHandle(GenericRequestDto requestDto) {
        return requestDto instanceof MessageRequestDto;
    }
}
