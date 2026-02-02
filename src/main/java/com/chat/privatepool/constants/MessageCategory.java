package com.chat.privatepool.constants;

import lombok.Getter;

@Getter
public enum MessageCategory {
    TOPIC_UNREAD_SUMMARY("TOPIC_UNREAD_SUMMARY"),
    NEW_MESSAGE("NEW_MESSAGE");

    private final String val;

    MessageCategory(String val) {
        this.val = val;
    }
}