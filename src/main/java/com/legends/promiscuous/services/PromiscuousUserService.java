package com.legends.promiscuous.services;

import com.legends.promiscuous.config.AppConfig;
import com.legends.promiscuous.dtos.requests.EmailNotificationRequest;
import com.legends.promiscuous.dtos.requests.Recipient;
import com.legends.promiscuous.dtos.requests.RegisterUserRequest;
import com.legends.promiscuous.dtos.response.ActivateAccountResponse;
import com.legends.promiscuous.dtos.response.ApiResponse;
import com.legends.promiscuous.dtos.response.GetUserResponse;
import com.legends.promiscuous.dtos.response.RegisterUserResponse;
import com.legends.promiscuous.exceptions.PromiscuousBaseException;
import com.legends.promiscuous.models.User;
import com.legends.promiscuous.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.legends.promiscuous.utils.AppUtils.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class PromiscuousUserService implements UserService{
    private final UserRepository userRepository;
    private final MailService mailService;
    private final AppConfig appConfig;

    @Override
    public RegisterUserResponse register(RegisterUserRequest registerUserRequest) {
        //1. extract registration details from the registration form(registerUserRequest)
        String email = registerUserRequest.getEmail();
        String password = registerUserRequest.getPassword();
        //2. create a user profile with the registration details
        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        //3. save that users profile in the Database
        User savedUser = userRepository.save(user);
        log.info("saved guy-->{}", savedUser);

        //4. send verification token to the users email
        EmailNotificationRequest request = buildEmailRequest(savedUser);
        mailService.send(request);

        //5. return a response
        RegisterUserResponse registerUserResponse = new RegisterUserResponse();
        registerUserResponse.setMessage("Registration Successful, check your mailbox to activate your account");

        return registerUserResponse;
    }

    @Override
    public ApiResponse<?> activateUserAccount(String token) {
        boolean isTestToken = token.equals(appConfig.getTestToken());
        if(isTestToken){
            ApiResponse<?> activateAccountResponse =
                    ApiResponse.builder()
//                    .data(new ActivateAccountResponse("Account activation successful",))
                    .build();
            return activateAccountResponse;
        }

        boolean isValidJwtToken = validateToken(token);
        if(isValidJwtToken){
            String email = extractEmailFrom(token);
            User foundUser = userRepository.findByEmail(email).orElseThrow();
            foundUser.setActive(true);
            User savedUser = userRepository.save(foundUser);
            GetUserResponse userResponse = GetUserResponse.builder()
                    .id(savedUser.getId())
                    .address(savedUser.getAddress().toString())
                    .fullName(savedUser.getFirstName() + " "+savedUser.getLastName())
                    .email(savedUser.getEmail())
                    .phoneNumber(savedUser.getPhoneNumber())
                    .build();
            var activateUserResponse = ActivateAccountResponse.builder()
                    .message("Account activation successful")
                    .user(userResponse)
                    .build();
            ApiResponse<?> activateAccountResponse =
                    ApiResponse.builder()
                            .data(activateUserResponse)
                            .build();
            return activateAccountResponse;
        }
        throw  new PromiscuousBaseException("Account activation was not successful");
    }

    private EmailNotificationRequest buildEmailRequest(User savedUser) {
        EmailNotificationRequest request =  new EmailNotificationRequest();
        List<Recipient> recipients = new ArrayList<>();
        Recipient recipient = new Recipient(savedUser.getEmail());
        recipients.add(recipient);
        request.setRecipients(recipients);
        request.setSubject(WELCOME_MAIL_SUBJECT);
        String activationLink = generateActivationLink(savedUser.getEmail());
        String emailTemplate = getMailTemplate();
        String mailContent = String.format(emailTemplate,activationLink);
        request.setMailContent(mailContent);

        return request;
    }
}
