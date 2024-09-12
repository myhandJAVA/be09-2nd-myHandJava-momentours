package com.myhandjava.momentours.common;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CommonException extends RuntimeException{
    private final HttpStatusCode statusCode;

    @Override
    public String getMessage() {
        return this.statusCode.getMessage();
    }
}
