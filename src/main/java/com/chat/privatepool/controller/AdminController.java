package com.chat.privatepool.controller;

import com.chat.privatepool.dto.request.RequestDto;
import com.chat.privatepool.dto.response.CommonResponseObject;
import com.chat.privatepool.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @GetMapping("/get-requests/{topicId}")
    public CommonResponseObject getPendingRequests(@PathVariable long topicId) {
        return adminService.getPendingRequests(topicId);
    }

    @PostMapping("/approval")
    public CommonResponseObject adminApproval(@RequestBody RequestDto requestDto) {
        return adminService.adminApproval(requestDto);
    }
}
