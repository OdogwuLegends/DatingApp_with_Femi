package com.legends.promiscuous.services;

import com.github.fge.jsonpatch.JsonPatch;
import com.legends.promiscuous.dtos.requests.LoginRequest;
import com.legends.promiscuous.dtos.requests.RegisterUserRequest;
import com.legends.promiscuous.dtos.requests.UpdateUserRequest;
import com.legends.promiscuous.dtos.response.*;

import java.util.List;

public interface UserService {
    RegisterUserResponse register(RegisterUserRequest service);

    LoginResponse login(LoginRequest loginRequest);

    ApiResponse<?> activateUserAccount(String token);

    GetUserResponse getUserById(long id);

    List<GetUserResponse> getAllUsers(int page, int pageSize);
    void deleteAll();
    UpdateUserResponse updateProfile(UpdateUserRequest updateUserRequest, Long id);

//    UpdateUserResponse updateUserProfile(JsonPatch jsonPatch, Long id);
}
