package com.chat.privatepool.controller;

import com.chat.privatepool.constants.JoinPolicy;
import com.chat.privatepool.dto.response.CommonResponseObject;
import com.chat.privatepool.service.TopicService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/topic")
@RequiredArgsConstructor
public class TopicController {

    private final TopicService topicService;

    @GetMapping("/list/{type}")
    public CommonResponseObject getTopicList(@PathVariable JoinPolicy type) {
        return topicService.getTopicList(type);
    }
}
