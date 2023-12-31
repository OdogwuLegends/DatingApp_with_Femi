package com.legends.promiscuous.services;

import com.legends.promiscuous.dtos.requests.MediaReactionRequest;
import com.legends.promiscuous.dtos.requests.RegisterUserRequest;
import com.legends.promiscuous.dtos.response.RegisterUserResponse;
import com.legends.promiscuous.dtos.response.UploadMediaResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static com.legends.promiscuous.enums.Reaction.DISLIKE;
import static com.legends.promiscuous.enums.Reaction.LIKE;
import static com.legends.promiscuous.utils.AppUtil.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PromiscuousMediaServiceTest {
    @Autowired
    private MediaService mediaService;
    @Autowired
    private UserService userService;


    @Test
    void testToUploadProfilePicture(){
        Path testPath = Paths.get(FIRST_PROFILE_PICTURE_FOR_TEST);
        try(InputStream inputStream = Files.newInputStream(testPath)){
            MultipartFile multipartFile = new MockMultipartFile("test",inputStream);
            UploadMediaResponse uploadProfilePicture = mediaService.uploadProfilePicture(multipartFile);
            assertThat(uploadProfilePicture).isNotNull();
        } catch (IOException exception){
            throw new RuntimeException(MEDIA_UPLOAD_FAILED_MSG);
        }
    }
    @Test
    void testVideoCannotBeUploadedWhereProfilePictureRequired(){
        Path testPath = Paths.get(VIDEO_PATH_FOR_TEST);
        try(InputStream inputStream = Files.newInputStream(testPath)){
            MultipartFile multipartFile = new MockMultipartFile("test",inputStream);
            assertThrows(RuntimeException.class,()->mediaService.uploadProfilePicture(multipartFile));
        } catch (IOException exception){
            throw new RuntimeException(MEDIA_UPLOAD_FAILED_MSG);
        }
    }

    @Test
    void testToUploadVideo(){
        Path testPath = Paths.get(VIDEO_PATH_FOR_TEST);
        try(InputStream inputStream = Files.newInputStream(testPath)){
            MultipartFile multipartFile = new MockMultipartFile("test",inputStream);
            UploadMediaResponse uploadedVideo = mediaService.uploadMedia(multipartFile, null);
            assertThat(uploadedVideo).isNotNull();
        } catch (IOException exception){
            throw new RuntimeException(MEDIA_UPLOAD_FAILED_MSG);
        }
    }

    @Test
    void testToUploadImage(){
        Path testPath = Paths.get(SECOND_PROFILE_PICTURE_FOR_TEST);
        try(InputStream inputStream = Files.newInputStream(testPath)){
            MultipartFile multipartFile = new MockMultipartFile("test",inputStream);
            UploadMediaResponse uploadedImage = mediaService.uploadMedia(multipartFile,null);
            assertThat(uploadedImage).isNotNull();
        } catch (IOException exception){
            throw new RuntimeException(MEDIA_UPLOAD_FAILED_MSG);
        }
    }

    @Test
    void testToLikeMedia(){
        RegisterUserRequest registerUserRequest = new RegisterUserRequest();
        registerUserRequest.setEmail("hello@gmail.com");
        registerUserRequest.setPassword("password");
        RegisterUserResponse registerUserResponse = userService.register(registerUserRequest);
        assertNotNull(registerUserResponse);
        assertNotNull(registerUserResponse.getMessage());

        String response = mediaService.reactToMedia(new MediaReactionRequest(LIKE,500L,501L));
        assertThat(response).isNotNull();
    }

}