package com.legends.promiscuous.services;

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
        String profilePicturePath = "C:\\Users\\USER\\Desktop\\SPRINGBOOT\\promiscuous\\src\\test\\resources\\images\\cloud.jpg";
        Path testPath = Paths.get(profilePicturePath);
        try(InputStream inputStream = Files.newInputStream(testPath)){
            MultipartFile multipartFile = new MockMultipartFile("test",inputStream);
            UploadMediaResponse uploadProfilePicture = mediaService.uploadProfilePicture(multipartFile);
            assertThat(uploadProfilePicture).isNotNull();
        } catch (IOException exception){
            throw new RuntimeException("Media upload failed");
        }
    }
    @Test
    void testVideoCannotBeUploadedWhereProfilePictureRequired(){
        String videoPath = "C:\\Users\\USER\\Desktop\\SPRINGBOOT\\promiscuous\\src\\test\\resources\\images\\WhatsApp Video 2023-08-22 at 22.03.22.mp4";
        Path testPath = Paths.get(videoPath);
        try(InputStream inputStream = Files.newInputStream(testPath)){
            MultipartFile multipartFile = new MockMultipartFile("test",inputStream);
            assertThrows(RuntimeException.class,()->mediaService.uploadProfilePicture(multipartFile));
        } catch (IOException exception){
            throw new RuntimeException("Media upload failed");
        }
    }

    @Test
    void testToUploadVideo(){
        String videoPath = "C:\\Users\\USER\\Desktop\\SPRINGBOOT\\promiscuous\\src\\test\\resources\\images\\WhatsApp Video 2023-08-22 at 22.03.22.mp4";
        Path testPath = Paths.get(videoPath);
        try(InputStream inputStream = Files.newInputStream(testPath)){
            MultipartFile multipartFile = new MockMultipartFile("test",inputStream);
            UploadMediaResponse uploadedVideo = mediaService.uploadMedia(multipartFile);
            assertThat(uploadedVideo).isNotNull();
        } catch (IOException exception){
            throw new RuntimeException("Media upload failed");
        }
    }

    @Test
    void testToUploadImage(){
        String image = "C:\\Users\\USER\\Desktop\\SPRINGBOOT\\promiscuous\\src\\test\\resources\\images\\planeone.jpg";
        Path testPath = Paths.get(image);
        try(InputStream inputStream = Files.newInputStream(testPath)){
            MultipartFile multipartFile = new MockMultipartFile("test",inputStream);
            UploadMediaResponse uploadedImage = mediaService.uploadMedia(multipartFile);
            assertThat(uploadedImage).isNotNull();
        } catch (IOException exception){
            throw new RuntimeException("Media upload failed");
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

        String response = mediaService.likeOrDislike(LIKE,registerUserResponse.getId());
        assertThat(response).isNotNull();
        assertEquals("Liked!",response);
    }

    @Test
    void testToDislikeMedia(){
        RegisterUserRequest registerUserRequest = new RegisterUserRequest();
        registerUserRequest.setEmail("sayhello@gmail.com");
        registerUserRequest.setPassword("password");
        RegisterUserResponse registerUserResponse = userService.register(registerUserRequest);
        assertNotNull(registerUserResponse);
        assertNotNull(registerUserResponse.getMessage());

        String response = mediaService.likeOrDislike(LIKE,registerUserResponse.getId());
        assertThat(response).isNotNull();
        assertEquals("Liked!",response);

        response = mediaService.likeOrDislike(DISLIKE,registerUserResponse.getId());
        assertThat(response).isNotNull();
        assertEquals("X",response);
    }
}