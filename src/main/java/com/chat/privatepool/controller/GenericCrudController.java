package com.chat.privatepool.controller;

import com.chat.privatepool.dto.request.GenericRequestDto;
import com.chat.privatepool.dto.response.CommonResponseObject;
import com.chat.privatepool.service.GenericServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/crud")
@RequiredArgsConstructor
public class GenericCrudController {

    private final GenericServiceImpl genericService;

    @PostMapping("/save")
    public CommonResponseObject save(@RequestBody GenericRequestDto dto) {
        return genericService.save(dto);
    }

    @PostMapping("/update")
    public CommonResponseObject update(@RequestBody GenericRequestDto dto) {
        return genericService.update(dto);
    }

    @PostMapping("/get")
    public CommonResponseObject get(@RequestBody GenericRequestDto dto) {
        return genericService.get(dto);
    }

    @PostMapping("/get-all")
    public CommonResponseObject getAll(@RequestBody GenericRequestDto dto) {
        return genericService.getAll(dto);
    }
}
