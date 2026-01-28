package com.chat.privatepool.constants;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserContext {
    private Long userId;
    private String userType;
    private String displayName;
}
