package com.legends.promiscuous.services;

import com.legends.promiscuous.dtos.requests.LoginRequest;
import com.legends.promiscuous.dtos.requests.RegisterUserRequest;
import com.legends.promiscuous.dtos.requests.UpdateUserRequest;
import com.legends.promiscuous.dtos.response.ApiResponse;
import com.legends.promiscuous.dtos.response.GetUserResponse;
import com.legends.promiscuous.dtos.response.LoginResponse;
import com.legends.promiscuous.dtos.response.RegisterUserResponse;
import com.legends.promiscuous.exceptions.BadCredentialsException;
import com.legends.promiscuous.repositories.AddressRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@ActiveProfiles("test")
@Sql(scripts = {"/db/insert.sql"})
public class UserServiceTest {
    @Autowired
    private UserService userService;
    @Autowired
    private AddressRepository addressRepository;

//    @AfterEach
//    void tearDown(){
//        userService.deleteAll();
//        addressRepository.deleteAll();
//    }
//    private RegisterUserRequest registerUserRequest;
//    private RegisterUserResponse registerUserResponse;

//    @BeforeEach
//    void setUp(){
//        registerUserRequest = new RegisterUserRequest();
//        registerUserRequest.setEmail("xogov34623@vreaa.com");
//        registerUserRequest.setPassword("password");
//    }


    @Test
    public void testThatUserCanRegister(){
//        registerUserResponse = userService.register(registerUserRequest);
//        assertNotNull(registerUserResponse);
//        assertNotNull(registerUserResponse.getMessage());

        RegisterUserRequest registerUserRequest = new RegisterUserRequest();
        registerUserRequest.setEmail("xogov34623@vreaa.com");
        registerUserRequest.setPassword("password");
        RegisterUserResponse registerUserResponse = userService.register(registerUserRequest);
        assertNotNull(registerUserResponse);
        assertNotNull(registerUserResponse.getMessage());
    }

    @Test
    public void testActivateUserAccount(){
//        registerUserRequest.setEmail("test@gmail.com");
//        registerUserResponse = userService.register(registerUserRequest);
//        assertNotNull(registerUserResponse);
//
//        ApiResponse<?> activateUserAccountResponse = userService.activateUserAccount("abc1234.erytuuoi.67t75646");
//        assertThat(activateUserAccountResponse).isNotNull();

        ApiResponse<?> activateUserAccountResponse = userService.activateUserAccount("abc1234.erytuuoi.67t75646");
        assertThat(activateUserAccountResponse).isNotNull();
    }

    @Test
    public void getUserByIdTest(){
//        userService.register(registerUserRequest);
//        GetUserResponse response = userService.getUserById(8L);
//        assertThat(response).isNotNull();
//        assertThat(response.getEmail()).isEqualTo(registerUserRequest.getEmail());

        GetUserResponse response = userService.getUserById(500L);
        assertThat(response).isNotNull();
    }
    @Test
    public void getAllUsers(){
//        registerTestUsers();
//        List<GetUserResponse> users = userService.getAllUsers(1,5);
//        assertThat(users).isNotNull();
//        assertThat(users.size()).isEqualTo(5);


        List<GetUserResponse> users = userService.getAllUsers(1,5);
        assertThat(users).isNotNull();
        assertThat(users.size()).isEqualTo(5);
    }

    @Test
    public void testThatUsersCanLogin(){
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("test1@email.com");
        loginRequest.setPassword("password");
        LoginResponse response = userService.login(loginRequest);
        assertThat(response).isNotNull();
        String token = response.getAccessToken();
        assertThat(token).isNotNull();
    }

    @Test
    public void testThatExceptionIsThrownWhenUserAuthenticatesWithBadCredentials(){
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("test1@email.com");
        loginRequest.setPassword("bad_password");

        assertThatThrownBy(()->userService.login(loginRequest)).isInstanceOf(BadCredentialsException.class);
    }

    @Test
    public void testThatUserCanUpdateAccount(){
        UpdateUserRequest updateUserRequest = new UpdateUserRequest();
        updateUserRequest.setId(500L);
        updateUserRequest.setDateOfBirth(LocalDate.of(2005, Month.NOVEMBER.ordinal(),25));
        updateUserRequest.setFirstName("Sheriff");
        updateUserRequest.setProfileImage();
    }

//    private void registerTestUsers() {
//        RegisterUserRequest firstRequest = new RegisterUserRequest();
//        firstRequest.setEmail("john@gmail.com");
//        firstRequest.setPassword("password");
//        userService.register(firstRequest);
//
//        firstRequest.setEmail("jane@gmail.com");
//        firstRequest.setPassword("password");
//        userService.register(firstRequest);
//
//        firstRequest.setEmail("jerry@gmail.com");
//        firstRequest.setPassword("password");
//        userService.register(firstRequest);
//
//        firstRequest.setEmail("johnny@gmail.com");
//        firstRequest.setPassword("password");
//        userService.register(firstRequest);
//
//        firstRequest.setEmail("jeoy@gmail.com");
//        firstRequest.setPassword("password");
//        userService.register(firstRequest);
//
//        firstRequest.setEmail("zaza@gmail.com");
//        firstRequest.setPassword("password");
//        userService.register(firstRequest);
//
//    }
}
