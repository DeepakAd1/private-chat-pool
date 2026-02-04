package com.chat.privatepool.util;

import com.chat.privatepool.object.Topic;
import com.chat.privatepool.repository.TopicDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import tools.jackson.core.JacksonException;
import tools.jackson.databind.ObjectMapper;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Map;

@Slf4j
public class CommonUtil {

    @Autowired
    private static TopicDao topicDao;

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

    public static LocalDateTime getCurrentTime() {
        return LocalDateTime.now();
    }

    public static boolean isAdmin(Long userId, Long topicId) {
        Topic topic = topicDao.findById(topicId).orElse(null);
        if (topic == null) return false;
        return userId.equals(topic.getCreatedBy());
    }
}
