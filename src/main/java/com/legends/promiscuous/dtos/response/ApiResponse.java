package com.legends.promiscuous.dtos.response;

import lombok.Builder;

@Builder
public class ApiResponse<T> {
    private T data;
}
