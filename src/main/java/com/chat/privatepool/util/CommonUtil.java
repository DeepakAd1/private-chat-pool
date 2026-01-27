package com.chat.privatepool.util;

import lombok.extern.slf4j.Slf4j;
import tools.jackson.core.JacksonException;
import tools.jackson.databind.ObjectMapper;

import java.util.Collection;
import java.util.Map;

@Slf4j
public class CommonUtil {

    public static <T> T createNewReference(ObjectMapper objectMapper, T obj) {
        try {
            if (obj == null) {
                return null;
            }
            return objectMapper.readValue(objectMapper.writeValueAsString(obj), (Class<T>) obj.getClass());
        } catch (JacksonException e) {
            log.error("Error while deep copy object {} in createNewReference() ", obj);
            return null;
        }
    }

    public static boolean isEmptyStr(String str) {
        return str == null || "".equalsIgnoreCase(str.trim());
    }

    public static <T> boolean isNonPrimitiveEmpty(T obj) {
        if (obj == null) return true;

        if (obj instanceof Number) {
            return ((Number) obj).doubleValue() <= 0;
        }

        if (obj instanceof CharSequence) {
            return ((CharSequence) obj).toString().trim().isEmpty();
        }

        if (obj instanceof Collection) {
            return ((Collection<?>) obj).isEmpty();
        }

        if (obj instanceof Map) {
            return ((Map<?, ?>) obj).isEmpty();
        }

        return false;
    }
}
