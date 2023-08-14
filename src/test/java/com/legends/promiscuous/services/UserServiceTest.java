package com.legends.promiscuous.services;

import com.legends.promiscuous.dtos.requests.RegisterUserRequest;
import com.legends.promiscuous.dtos.response.ApiResponse;
import com.legends.promiscuous.dtos.response.GetUserResponse;
import com.legends.promiscuous.dtos.response.RegisterUserResponse;
import com.legends.promiscuous.models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@ActiveProfiles("test")
public class UserServiceTest {
    @Autowired
    private UserService userService;
    private RegisterUserRequest registerUserRequest;
    private RegisterUserResponse registerUserResponse;

    @BeforeEach
    void setUp(){
        registerUserRequest = new RegisterUserRequest();
        registerUserRequest.setEmail("xogov34623@vreaa.com");
        registerUserRequest.setPassword("password");
    }


    @Test
    public void testThatUserCanRegister(){
        registerUserResponse = userService.register(registerUserRequest);
        assertNotNull(registerUserResponse);
        assertNotNull(registerUserResponse.getMessage());
    }

    @Test
    public void testActivateUserAccount(){
        registerUserRequest.setEmail("test@gmail.com");
        registerUserResponse = userService.register(registerUserRequest);
        assertNotNull(registerUserResponse);

        ApiResponse<?> activateUserAccountResponse = userService.activateUserAccount("abc1234.erytuuoi.67t75646");
        assertThat(activateUserAccountResponse).isNotNull();
    }

    @Test
    public void getUserByIdTest(){
        userService.register(registerUserRequest);
        GetUserResponse response = userService.getUserById(1L);
        assertThat(response).isNotNull();
        assertThat(response.getEmail()).isEqualTo(registerUserRequest.getEmail());
    }
    @Test
    public void getAllUsers(){
        List<User> users = userService.getAllUsers(1,5);
    }
}
