package com.chat.privatepool.dto.response;

import com.chat.privatepool.constants.UserType;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GuestLoginResponse {
    private Long userId;
    private String defaultName;
    private String token;
    private UserType userType;
}
