package com.legends.promiscuous.utils;

import com.legends.promiscuous.exceptions.PromiscuousBaseException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static com.legends.promiscuous.utils.JwtUtil.generateToken;

public class AppUtil {
    public static final String APP_NAME = "Promiscuous inc";
    public static final String APP_EMAIL = "noreply@promiscuous.africa";
    public static final String WELCOME_MAIL_SUBJECT = "Welcome to Promiscuous inc.";
    private static final String MAIL_TEMPLATE_LOCATION = "C:\\Users\\USER\\Desktop\\SPRINGBOOT\\promiscuous\\src\\main\\resources\\templates\\index (4).html";
    public static final String BLANK_SPACE =  " ";
    public static final String EMPTY_STRING = "";
    private static final String ACTIVATE_ACCOUNT_PATH = "/user/activate?code=";

    public static String generateActivationLink(String baseUrl,String email){
        String token = generateToken(email);
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


}
