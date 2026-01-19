package com.chat.privatepool.strategy;

import com.chat.privatepool.dto.request.GenericRequestDto;

public interface GenericCrudOps<T> {

    T save(GenericRequestDto requestDto);
    T update(GenericRequestDto requestDto);
    T remove(GenericRequestDto requestDto);
    T get(GenericRequestDto requestDto);
    T getAll(GenericRequestDto requestDto);

    public boolean canHandle(GenericRequestDto requestDto);

}
