package com.chat.privatepool.controller;

import com.chat.privatepool.dto.response.CommonResponseObject;
import com.chat.privatepool.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/message")
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;

    @PostMapping("/get-messages/{topicId}")
    public CommonResponseObject getMessages(@PathVariable("topicId") int topicId,
                                            @RequestParam(defaultValue = "1", required = false) int page,
                                            @RequestParam(defaultValue = "10", required = false) int limit) {
        return messageService.getMessagesByTopic(topicId, page, limit);
    }
}
