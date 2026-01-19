package com.chat.privatepool.service;

import com.chat.privatepool.dto.request.GenericRequestDto;
import com.chat.privatepool.strategy.GenericCrudOps;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ConfigHandler {

    private final List<GenericCrudOps> configMap;

    public GenericCrudOps getConfigHandler(GenericRequestDto requestDto) throws Exception {
        for (GenericCrudOps g : configMap) {
            System.out.println(g);
        }
        return configMap.stream()
                .filter(handler -> handler.canHandle(requestDto))
                .findFirst()
                .orElseThrow(() -> new Exception("No handler found for " + requestDto.getType()));
    }
}
