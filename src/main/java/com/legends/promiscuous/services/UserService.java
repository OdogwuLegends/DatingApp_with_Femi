package com.legends.promiscuous.services;

import com.legends.promiscuous.dtos.requests.RegisterUserRequest;
import com.legends.promiscuous.dtos.response.ApiResponse;
import com.legends.promiscuous.dtos.response.GetUserResponse;
import com.legends.promiscuous.dtos.response.RegisterUserResponse;
import com.legends.promiscuous.models.User;

import java.util.List;

public interface UserService {
    RegisterUserResponse register(RegisterUserRequest service);

    ApiResponse<?> activateUserAccount(String token);

    GetUserResponse getUserById(long id);

    List<User> getAllUsers(int page, int pageSize);
}
