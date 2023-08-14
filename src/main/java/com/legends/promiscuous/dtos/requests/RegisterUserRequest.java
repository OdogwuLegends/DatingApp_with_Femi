package com.legends.promiscuous.dtos.requests;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class RegisterUserRequest {
    private String email;
    private String password;
}
