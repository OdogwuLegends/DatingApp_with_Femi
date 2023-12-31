package com.legends.promiscuous.utils;

import com.legends.promiscuous.exceptions.PromiscuousBaseException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static com.legends.promiscuous.utils.JwtUtil.generateVerificationToken;

public class AppUtil {
    public static final String APP_NAME = "Promiscuous inc";
    public static final String APP_EMAIL = "noreply@promiscuous.africa";
    public static final String WELCOME_MAIL_SUBJECT = "Welcome to Promiscuous inc.";
    private static final String MAIL_TEMPLATE_LOCATION = "C:\\Users\\USER\\Desktop\\SPRINGBOOT\\promiscuous\\src\\main\\resources\\templates\\index (4).html";
    public static final String TEST_IMG_LOCATION = "C:\\Users\\USER\\Desktop\\SPRINGBOOT\\promiscuous\\src\\test\\resources\\images\\stockphoto.jpg";
    public static final String BLANK_SPACE =  " ";
    public static final String EMPTY_STRING = "";
    private static final String ACTIVATE_ACCOUNT_PATH = "/user/activate?code=";
    public static final String JSON_PATCH_PATH_PREFIX = "/";
    public static final String PROFILE_PICTURE_UPDATED_MSG = "Profile picture updated";
    public static final String LIKED_MSG = "Liked!";
    public static final String DISLIKED_MSG = "X";
    public static final String MEDIA_UPLOAD_SUCCESSFUL = "Media upload successful";
    public static final String FILE_UPLOAD_FAILED_MSG = "File upload failed: ";
    public static final String MEDIA_UPLOAD_FAILED_MSG = "Media upload failed";
    public static final String FIRST_PROFILE_PICTURE_FOR_TEST = "C:\\Users\\USER\\Desktop\\SPRINGBOOT\\promiscuous\\src\\test\\resources\\images\\cloud.jpg";
    public static final String SECOND_PROFILE_PICTURE_FOR_TEST = "C:\\Users\\USER\\Desktop\\SPRINGBOOT\\promiscuous\\src\\test\\resources\\images\\planeone.jpg";
    public static final String VIDEO_PATH_FOR_TEST = "C:\\Users\\USER\\Desktop\\SPRINGBOOT\\promiscuous\\src\\test\\resources\\images\\WhatsApp Video 2023-08-22 at 22.03.22.mp4";
    public static String generateActivationLink(String baseUrl,String email){
        String token = generateVerificationToken(email);
        String activationLink = baseUrl + ACTIVATE_ACCOUNT_PATH + token;
        return activationLink;
    }

    public static boolean matches(String first, String second){
        return first.equals(second);
    }

    public static String getMailTemplate(){
        Path templateLocation = Paths.get(MAIL_TEMPLATE_LOCATION);

        try{
            List<String> fileContents = Files.readAllLines(templateLocation);
            return String.join(EMPTY_STRING, fileContents);
        } catch (IOException e){
            throw new PromiscuousBaseException(e.getMessage());
        }
    }
    public static List<String> getPublicPaths(){
        return List.of("/api/v1/user/register","/login");
    }

}
