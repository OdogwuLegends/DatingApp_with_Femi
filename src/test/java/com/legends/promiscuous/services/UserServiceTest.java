package com.legends.promiscuous.services;

import com.legends.promiscuous.dtos.requests.LoginRequest;
import com.legends.promiscuous.dtos.requests.RegisterUserRequest;
import com.legends.promiscuous.dtos.requests.UpdateUserRequest;
import com.legends.promiscuous.dtos.response.*;
import com.legends.promiscuous.exceptions.BadCredentialsException;
import com.legends.promiscuous.exceptions.PromiscuousBaseException;
import com.legends.promiscuous.repositories.AddressRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Set;

import static com.legends.promiscuous.utils.AppUtil.BLANK_SPACE;
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
        UpdateUserRequest updateUserRequest = buildUpdateRequest();

        UpdateUserResponse response = userService.updateProfile(updateUserRequest, 500L);

        assertThat(response).isNotNull();
        GetUserResponse userResponse = userService.getUserById(500L);

        String fullName = userResponse.getFullName();
        String expectedFullName = new StringBuilder()
                .append(updateUserRequest.getFirstName())
                .append(BLANK_SPACE)
                .append(updateUserRequest.getLastName()).toString();

        assertThat(fullName).isEqualTo(expectedFullName);

    }

    private UpdateUserRequest buildUpdateRequest() {
        Set<String> interests = Set.of("Swimming", "Sports", "Cooking");
        UpdateUserRequest updateUserRequest =  new UpdateUserRequest();
        updateUserRequest.setDateOfBirth(LocalDate.of(2005, Month.NOVEMBER.ordinal(),25));
        updateUserRequest.setFirstName("Sheriff");
        updateUserRequest.setLastName("Awofiranye");
        MultipartFile testImage = getTestImage();
        updateUserRequest.setProfileImage(testImage);
        updateUserRequest.setInterests(interests);
        return updateUserRequest;
    }

    private MultipartFile getTestImage(){
        //obtain a path that points to the image
        Path path = Paths.get("C:\\Users\\USER\\Desktop\\SPRINGBOOT\\promiscuous\\src\\test\\resources\\images\\airplane_cartoon.png");
        //create stream that can read bytes from file pointed to by path
        try (var inputStream = Files.newInputStream(path)){
            //create a multipartFile using bytes from the inputStream obtained from the path
            MultipartFile image = new MockMultipartFile("test_image",inputStream);
          return image;
        } catch (Exception exception){
            throw new PromiscuousBaseException(exception.getMessage());
        }
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
