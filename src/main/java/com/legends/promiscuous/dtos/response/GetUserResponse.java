package com.legends.promiscuous.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Builder;

@AllArgsConstructor
@Builder
public class GetUserResponse {
    private Long id;
    private String email;
    private String fullName;
    private String phoneNumber;
    private String address;
}
