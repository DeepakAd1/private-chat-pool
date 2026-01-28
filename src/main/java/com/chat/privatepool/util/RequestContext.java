package com.chat.privatepool.util;

import com.chat.privatepool.constants.UserContext;

public class RequestContext {

    private static final ThreadLocal<UserContext> CONTEXT = new ThreadLocal<>();

    public static void set(UserContext user) {
        CONTEXT.set(user);
    }

    public static UserContext get() {
        return CONTEXT.get();
    }

    public static void clear() {
        CONTEXT.remove();
    }
}
