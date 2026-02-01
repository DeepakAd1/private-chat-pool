package com.chat.privatepool.constants;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class WebSocketPayload {
    private String type;
    private Object data;
}