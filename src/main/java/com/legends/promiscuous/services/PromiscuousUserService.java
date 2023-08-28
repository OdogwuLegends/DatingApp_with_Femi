package com.legends.promiscuous.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.TextNode;
import com.github.fge.jackson.jsonpointer.JsonPointer;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import com.github.fge.jsonpatch.JsonPatchOperation;
import com.github.fge.jsonpatch.ReplaceOperation;
import com.legends.promiscuous.config.AppConfig;
import com.legends.promiscuous.dtos.requests.*;
import com.legends.promiscuous.dtos.response.*;
import com.legends.promiscuous.enums.Interest;
import com.legends.promiscuous.enums.Role;
import com.legends.promiscuous.exceptions.AccountActivationFailedException;
import com.legends.promiscuous.exceptions.ExceptionMessage;
import com.legends.promiscuous.exceptions.PromiscuousBaseException;
import com.legends.promiscuous.exceptions.UserNotFoundException;
import com.legends.promiscuous.models.Address;
import com.legends.promiscuous.models.User;
import com.legends.promiscuous.repositories.UserRepository;
import com.legends.promiscuous.services.cloud.CloudService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

import static com.legends.promiscuous.dtos.response.ResponseMessage.PROFILE_UPDATE_SUCCESSFUL;
import static com.legends.promiscuous.enums.Role.ADMIN;
import static com.legends.promiscuous.enums.Role.CUSTOMER;
import static com.legends.promiscuous.exceptions.ExceptionMessage.USER_NOT_FOUND_EXCEPTION;
import static com.legends.promiscuous.exceptions.ExceptionMessage.USER_WITH_EMAIL_NOT_FOUND_EXCEPTION;
import static com.legends.promiscuous.utils.AppUtil.*;
import static com.legends.promiscuous.utils.JwtUtil.extractEmailFrom;
import static com.legends.promiscuous.utils.JwtUtil.validateToken;

@Service
@RequiredArgsConstructor
@Slf4j
public class PromiscuousUserService implements UserService{
    private final UserRepository userRepository;
    private final MailService mailService;
    private final AppConfig appConfig;
    private final CloudService cloudService;
    private final MediaService mediaService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public RegisterUserResponse register(RegisterUserRequest registerUserRequest) {
        String email = registerUserRequest.getEmail();
        String password = registerUserRequest.getPassword();
        String hashedPassword = passwordEncoder.encode(password);
        User user = new User();
        user.setEmail(email);
        user.setPassword(hashedPassword);
        user.setAddress(new Address());
        user.setRole(CUSTOMER);
        User savedUser = userRepository.save(user);

        EmailNotificationRequest request = buildEmailRequest(savedUser);
        mailService.send(request);

        RegisterUserResponse registerUserResponse = new RegisterUserResponse();
        registerUserResponse.setMessage(ResponseMessage.USER_REGISTRATION_SUCCESSFUL.name());

        return registerUserResponse;
    }

//    @Override
//    public LoginResponse login(LoginRequest loginRequest) {
//        String email = loginRequest.getEmail();
//        String password = loginRequest.getPassword();
//
//        Optional<User> foundUser = userRepository.findByEmail(email);
//        User user = foundUser.orElseThrow(()-> new UserNotFoundException(
//                String.format(USER_WITH_EMAIL_NOT_FOUND_EXCEPTION.getMessage(), email)
//        ));
//
//        boolean isValidPassword = AppUtil.matches(user.getPassword(),password);
//        if(isValidPassword) return buildLoginResponse(email);
//        throw new BadCredentialsException(INVALID_CREDENTIALS_EXCEPTION.getMessage());
//    }
//
//    private static LoginResponse buildLoginResponse(String email) {
//        String accessToken = generateToken(email);
//        LoginResponse loginResponse = new LoginResponse();
//        loginResponse.setAccessToken(accessToken);
//        return loginResponse;
//    }


    @Override
    public ApiResponse<?> activateUserAccount(String token) {
        boolean isTestToken = token.equals(appConfig.getTestToken());
        if (isTestToken ) return activateTestAccount();

        boolean isValidJwt = validateToken(token);
        if (isValidJwt) return activateAccount(token);
        throw new AccountActivationFailedException(ExceptionMessage.ACCOUNT_ACTIVATION_FAILED_EXCEPTION.getMessage());
    }

    @Override
    public GetUserResponse getUserById(long id) throws UserNotFoundException {
        Optional<User> foundUser = userRepository.findById(id);
        User user = foundUser.orElseThrow(()-> new UserNotFoundException(USER_NOT_FOUND_EXCEPTION.getMessage()));
        GetUserResponse getUserResponse = buildGetUserResponse(user);
        return getUserResponse;
    }

    @Override
    public List<GetUserResponse> getAllUsers(int page, int pageSize) {
        List<GetUserResponse> users = new ArrayList<>();

        Pageable pageable = buildPageRequest(page,pageSize);
        Page<User> usersPage = userRepository.findAll(pageable);
        List<User> foundUsers =  usersPage.getContent();

//        for(User user : foundUsers){
//            GetUserResponse getUserResponse = buildGetUserResponse(user);
//            users.add(getUserResponse);
//        }
//        return users;

                //OR USE LAMBDA
        return foundUsers.stream()
               // .map(PromiscuousUserService::buildGetUserResponse)
                .map(user -> buildGetUserResponse(user))
                .toList();
    }

//    @Override
//    public UpdateUserResponse updateUserProfile(JsonPatch jsonPatch, Long id){
//        ObjectMapper mapper = new ObjectMapper();
//        User user = findUserById(id);
//        JsonNode node = mapper.convertValue(user, JsonNode.class);
//
//        try {
//            JsonNode updatedNode = jsonPatch.apply(node);
//            User updatedUser = mapper.convertValue(updatedNode,User.class);
//            userRepository.save(updatedUser);
//            UpdateUserResponse response = new UpdateUserResponse();
//            response.setMessage("Update Successful");
//            return response;
//        } catch (JsonPatchException exception){
//            throw new PromiscuousBaseException(":(");
//        }
//    }

    @Override
    public UpdateUserResponse updateProfile(UpdateUserRequest updateUserRequest, Long id) {
        ModelMapper modelMapper = new ModelMapper();
        String url =  uploadImage(updateUserRequest.getProfileImage());

        User user = findUserById(id);

        Set<String> userInterests = updateUserRequest.getInterests();
        Set<Interest> interests = parseInterestsFrom(userInterests);
        user.setInterests(interests);

        Address userAddress = user.getAddress();
        modelMapper.map(updateUserRequest, userAddress);
        user.setAddress(userAddress);
        JsonPatch updatePatch = buildUpdatePatch(updateUserRequest);
        return applyPatch(updatePatch, user);
    }
    @Override
    public UploadMediaResponse uploadMedia(MultipartFile mediaToUpload) {
        return mediaService.uploadMedia(mediaToUpload);
    }
    @Override
    public UploadMediaResponse uploadProfilePicture(MultipartFile mediaToUpload) {
        return mediaService.uploadProfilePicture(mediaToUpload);
    }
    @Override
    public ApiResponse<?> reactToMedia(MediaReactionRequest mediaReactionRequest) {
        String response = mediaService.reactToMedia(mediaReactionRequest);
        return ApiResponse.builder().data(response).build();
    }

    @Override
    public GetUserResponse findUserByEmail(String email) {
        Optional<User> foundUser = userRepository.findByEmail(email);
        User user = foundUser.orElseThrow(()-> new UserNotFoundException(USER_NOT_FOUND_EXCEPTION.getMessage()));
        GetUserResponse getUserResponse = buildGetUserResponse(user);
        return getUserResponse;
    }

    @Override
    public AdminInvitationResponse inviteAdmin(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(()-> new UserNotFoundException(USER_NOT_FOUND_EXCEPTION.getMessage()));

        EmailNotificationRequest request = buildEmailRequest(user);
        mailService.send(request);
        return null;
    }

    @Override
    public ApiResponse<?> acceptAdminInvitation(String token) {

        boolean isTestToken = token.equals(appConfig.getTestToken());
        if (isTestToken ) return  makeTestAdmin();

        boolean isValidJwt = validateToken(token);
        if (isValidJwt) return makeAdmin(token);

        throw new AccountActivationFailedException(ExceptionMessage.ACCOUNT_ACTIVATION_FAILED_EXCEPTION.getMessage());

    }

    @Override
    public User getUserByEmail(String email) {
       return userRepository.findByEmail(email).orElseThrow(()-> new UserNotFoundException(String.format(USER_WITH_EMAIL_NOT_FOUND_EXCEPTION.getMessage(),email)));
    }

    @Override
    public GetUserResponse suspendUser(String email) {
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isEmpty()) {
            throw new RuntimeException("User dose not exists");
        }
        User user = optionalUser.get();
        user.setActive(false);
        userRepository.save(user);

        GetUserResponse getUserResponse = buildGetUserResponse(user);

        return getUserResponse;
    }

    private String uploadImage(MultipartFile newProfileImage) {
        boolean isFormWithProfileImage = newProfileImage != null;
        if(isFormWithProfileImage) return cloudService.upload(newProfileImage);
        throw new RuntimeException("Image upload failed");
    }

    private static Set<Interest> parseInterestsFrom(Set<String> interests){
       Set<Interest> userInterests =  interests.stream()
                                                .map(interest -> Interest.valueOf(interest.toUpperCase()))
                                                .collect(Collectors.toSet());

       return userInterests;
    }

    private UpdateUserResponse applyPatch(JsonPatch updatePatch, User user) {
        ObjectMapper objectMapper = new ObjectMapper();
        //1. Convert user to JsonNode
        JsonNode userNode = objectMapper.convertValue(user, JsonNode.class);
        try {
            //2. Apply patch to JsonNode from step 1
            JsonNode updatedNode = updatePatch.apply(userNode);
            //3. Convert updatedNode back to user
            User updatedUser = objectMapper.convertValue(updatedNode, User.class);
            //4. Save updated User
            userRepository.save(updatedUser);
            return  new UpdateUserResponse(PROFILE_UPDATE_SUCCESSFUL.name());
        }catch (JsonPatchException exception){
            throw new PromiscuousBaseException(exception.getMessage());
        }
    }

    private JsonPatch buildUpdatePatch(UpdateUserRequest updateUserRequest) {
        Field[] fields = updateUserRequest.getClass().getDeclaredFields();

        List<ReplaceOperation> operations = Arrays.stream(fields)
                                                    .filter(field -> validateFields(updateUserRequest, field))
                                                    .map(field->buildReplaceOperation(updateUserRequest, field))
                                                    .toList();

        List<JsonPatchOperation> patchOperations = new ArrayList<>(operations);
        return new JsonPatch(patchOperations);
    }

    private static boolean validateFields(UpdateUserRequest updateUserRequest, Field field) {
        List<String> list = List.of("interests","street","houseNumber","country","state", "gender","profileImage");
        field.setAccessible(true);
        try {
            return field.get(updateUserRequest) != null && !list.contains(field.getName());
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private static ReplaceOperation buildReplaceOperation(UpdateUserRequest updateUserRequest, Field field) {
        field.setAccessible(true);
        try {
            String path = JSON_PATCH_PATH_PREFIX + field.getName();
            JsonPointer pointer = new JsonPointer(path);
            String value = field.get(updateUserRequest).toString();
            TextNode node = new TextNode(value);
            ReplaceOperation operation = new ReplaceOperation(pointer, node);
            return operation;
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }

    public User findUserById(Long id){
        Optional<User> foundUser = userRepository.findById(id);
        User user = foundUser.orElseThrow(()-> new UserNotFoundException(USER_NOT_FOUND_EXCEPTION.getMessage()));
        return user;
    }

    private Pageable buildPageRequest(int page, int pageSize) {
        if(page < 1 && pageSize < 1) return PageRequest.of(0, 10);
        if(page < 1) return PageRequest.of(0, 10);
        if(pageSize < 1) return PageRequest.of(page,pageSize);
        return PageRequest.of(page - 1,pageSize);
    }


    private static ActivateAccountResponse buildActivateUserResponse(GetUserResponse userResponse) {
        return ActivateAccountResponse.builder()
                .message(ResponseMessage.ACCOUNT_ACTIVATION_SUCCESSFUL.name())
                .user(userResponse)
                .build();
    }

    private static GetUserResponse buildGetUserResponse(User savedUser) {
        return GetUserResponse.builder()
                .id(savedUser.getId())
                .address(savedUser.getAddress().toString())
                .fullName(getFullName(savedUser))
                .phoneNumber(savedUser.getPhoneNumber())
                .email(savedUser.getEmail())
                .isActive(savedUser.isActive())
                .role(savedUser.getRole())
                .build();
    }

    private static String getFullName(User savedUser) {
        return savedUser.getFirstName() + BLANK_SPACE + savedUser.getLastName();
    }
    private ApiResponse<?> makeAdmin(String token) {
        String email = extractEmailFrom(token);
        return buildMakeAdminBody(email);
    }
    private  ApiResponse<?> makeTestAdmin() {
        String email = "test@email.com";
        return buildMakeAdminBody(email);
    }
    public ApiResponse<?> buildMakeAdminBody(String email) {

        User savedUser = changeUserState(email, ADMIN);

        GetUserResponse userResponse = buildGetUserResponse(savedUser);
        var activateUserResponse = buildActivateUserResponse(userResponse);
        return ApiResponse.builder().data(activateUserResponse).build();
    }

    private User changeUserState(String email, Role role) {
        User foundUser = findUserToChangeState(email);

        if(foundUser.isActive()) foundUser.setRole(role);
        else throw new PromiscuousBaseException("User Is Not Yet Activated");

        User savedUser = userRepository.save(foundUser);
        return savedUser;
    }
    private User changeUserState(String email, boolean isActive) {
        User foundUser = findUserToChangeState(email);
        foundUser.setActive(isActive);
        User savedUser = userRepository.save(foundUser);
        return savedUser;
    }
    private User findUserToChangeState(String email){
       return userRepository.findByEmail(email)
                            .orElseThrow(() ->new UserNotFoundException(
                                    String.format(USER_WITH_EMAIL_NOT_FOUND_EXCEPTION.getMessage(), email)));
    }
    private  ApiResponse<?> activateTestAccount() {
            String email = "test@email.com";
        return buildActivationResponse(email);
    }
    private ApiResponse<?> activateAccount(String token) {
        String email = extractEmailFrom(token);
        return buildActivationResponse(email);
    }
    private ApiResponse<?> buildActivationResponse(String email) {
        User savedUser = changeUserState(email, true);

        GetUserResponse userResponse = buildGetUserResponse(savedUser);
        var activateUserResponse = buildActivateUserResponse(userResponse);
        return ApiResponse.builder().data(activateUserResponse).build();
    }

    private EmailNotificationRequest buildEmailRequest(User savedUser) {
        EmailNotificationRequest request = new EmailNotificationRequest();
        List<Recipient> recipients = new ArrayList<>();
        Recipient recipient = new Recipient(savedUser.getEmail());
        recipients.add(recipient);
        request.setRecipients(recipients);
        request.setSubject(WELCOME_MAIL_SUBJECT);
        String activationLink = generateActivationLink(appConfig.getBaseUrl(), savedUser.getEmail());
        String emailTemplate = getMailTemplate();

        String mailContent = String.format(emailTemplate, activationLink);
        request.setMailContent(mailContent);

        return request;
    }
}
