package com.chat.privatepool.strategy;

import com.chat.privatepool.dto.request.GenericRequestDto;
import com.chat.privatepool.dto.request.MessageRequestDto;
import com.chat.privatepool.dto.response.CommonResponseObject;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MessageStrategy implements GenericCrudOps<CommonResponseObject> {
    @Override
    public CommonResponseObject save(GenericRequestDto requestDto) {
        return null;
    }

    @Override
    public CommonResponseObject update(GenericRequestDto requestDto) {
        return null;
    }

    @Override
    public CommonResponseObject remove(GenericRequestDto requestDto) {
        return null;
    }

    @Override
    public CommonResponseObject get(GenericRequestDto requestDto) {
        return null;
    }

    @Override
    public CommonResponseObject getAll(GenericRequestDto requestDto) {
        return null;
    }

    @Override
    public boolean canHandle(GenericRequestDto requestDto) {
        return requestDto instanceof MessageRequestDto;
    }
}
