package com.tuhuynh.utils;

import lombok.AllArgsConstructor;
import lombok.Builder;

@Builder
@AllArgsConstructor
public class ResponseMessage {
    private final String message;
}
