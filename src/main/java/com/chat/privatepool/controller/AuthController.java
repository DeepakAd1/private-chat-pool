package com.chat.privatepool.controller;

import com.chat.privatepool.dto.response.GuestLoginResponse;
import com.chat.privatepool.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/guest")
    public ResponseEntity<GuestLoginResponse> guestLogin(
            @RequestParam(required = false) String nickName) {


        GuestLoginResponse response = authService.guestLogin(nickName);
        return ResponseEntity.ok(response);
    }
}
