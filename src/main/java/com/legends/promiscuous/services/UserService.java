package com.legends.promiscuous.services;

import com.github.fge.jsonpatch.JsonPatchException;
import com.legends.promiscuous.dtos.requests.LoginRequest;
import com.legends.promiscuous.dtos.requests.MediaReactionRequest;
import com.legends.promiscuous.dtos.requests.RegisterUserRequest;
import com.legends.promiscuous.dtos.requests.UpdateUserRequest;
import com.legends.promiscuous.dtos.response.*;
import com.legends.promiscuous.exceptions.UserNotFoundException;
import com.legends.promiscuous.models.User;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UserService {
    RegisterUserResponse register(RegisterUserRequest service);

//    LoginResponse login(LoginRequest loginRequest);

    ApiResponse<?> activateUserAccount(String token);

    User findUserById(Long id);

    GetUserResponse getUserById(long id) throws UserNotFoundException;

    List<GetUserResponse> getAllUsers(int page, int pageSize);
    GetUserResponse suspendUser(String email);
    UpdateUserResponse updateProfile(UpdateUserRequest updateUserRequest, Long id) throws JsonPatchException;
    UploadMediaResponse uploadMedia(MultipartFile mediaToUpload);
    UploadMediaResponse uploadProfilePicture(MultipartFile mediaToUpload);
    ApiResponse<?> reactToMedia(MediaReactionRequest mediaReactionRequest);

    GetUserResponse findUserByEmail(String email);

    AdminInvitationResponse inviteAdmin(String email);

    ApiResponse<?> acceptAdminInvitation(String token);

//    UpdateUserResponse updateUserProfile(JsonPatch jsonPatch, Long id);
}
