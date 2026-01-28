package com.chat.privatepool.service;

import com.chat.privatepool.dto.request.GenericRequestDto;
import com.chat.privatepool.dto.response.CommonResponseObject;
import com.chat.privatepool.strategy.GenericCrudOps;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class GenericServiceImpl {

    private final ConfigHandler handler;

    public CommonResponseObject save(GenericRequestDto genericRequestDto) {
        try {
            GenericCrudOps ops = handler.getConfigHandler(genericRequestDto);
            return (CommonResponseObject) ops.save(genericRequestDto);
        } catch (Exception e) {
            log.error("Error in saving {}", genericRequestDto.getType());
            return CommonResponseObject.setErrorMessage(e.getMessage());
        }
    }

    public CommonResponseObject update(GenericRequestDto dto) {
        try {
            GenericCrudOps ops = handler.getConfigHandler(dto);
            return (CommonResponseObject) ops.update(dto);
        } catch (Exception e) {
            log.error("Error in update {}", e.getMessage(), e);
            return CommonResponseObject.setErrorMessage(e.getMessage());
        }
    }

    public CommonResponseObject get(GenericRequestDto dto) {
        try {
            GenericCrudOps ops = handler.getConfigHandler(dto);
            return (CommonResponseObject) ops.get(dto);
        } catch (Exception e) {
            log.error("Error in get {}", e.getMessage(), e);
            return CommonResponseObject.setErrorMessage(e.getMessage());
        }
    }

    public CommonResponseObject getAll(GenericRequestDto dto) {
        try {
            GenericCrudOps ops = handler.getConfigHandler(dto);
            return (CommonResponseObject) ops.getAll(dto);
        } catch (Exception e) {
            log.error("Error in getAll {}", e.getMessage(), e);
            return CommonResponseObject.setErrorMessage(e.getMessage());
        }
    }
}
