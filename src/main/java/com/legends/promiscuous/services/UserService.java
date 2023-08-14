package com.legends.promiscuous.services;

import com.legends.promiscuous.dtos.requests.RegisterUserRequest;
import com.legends.promiscuous.dtos.response.ApiResponse;
import com.legends.promiscuous.dtos.response.GetUserResponse;
import com.legends.promiscuous.dtos.response.RegisterUserResponse;

public interface UserService {
    RegisterUserResponse register(RegisterUserRequest service);

    ApiResponse<?> activateUserAccount(String token);

    GetUserResponse getUserById(long id);
}
