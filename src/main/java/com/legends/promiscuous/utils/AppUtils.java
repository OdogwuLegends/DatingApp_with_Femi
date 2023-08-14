package com.legends.promiscuous.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.legends.promiscuous.exceptions.PromiscuousBaseException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.List;

public class AppUtils {
    public static final String APP_NAME = "Promiscuous inc";
    public static final String APP_EMAIL = "noreply@promiscuous.africa";
    public static final String WELCOME_MAIL_SUBJECT = "Welcome to Promiscuous inc.";
    private static final String MAIL_TEMPLATE_LOCATION = "C:\\Users\\USER\\Desktop\\SPRINGBOOT\\promiscuous\\src\\main\\resources\\templates\\index (4).html";
    public static String generateActivationLink(String email){
        String baseUrl = "http://localhost:8080";
        String urlActivatePath = "/activate";
        String queryStringPrefix = "?";
        String queryStringKey = "code=";
        String token = generateToken(email);

        return baseUrl + urlActivatePath + queryStringPrefix + queryStringKey + token;
    }
    public static String generateToken(String email){
        // generate token that has the user's email embedded in it
        //TODO: Refactor this, remove hardcoded values

        return  JWT.create()
                .withClaim("user", email)
                .withIssuer(APP_NAME)
                .withExpiresAt(Instant.now().plusSeconds(3600))
                .sign(Algorithm.HMAC512("secret"));

    }

    public static String getMailTemplate(){
        Path templateLocation = Paths.get(MAIL_TEMPLATE_LOCATION);

        try{
            List<String> fileContents = Files.readAllLines(templateLocation);
            return String.join("", fileContents);
        } catch (IOException e){
            throw new PromiscuousBaseException(e.getMessage());
        }
    }

    public static boolean validateToken(String token){
        JWTVerifier verifier = JWT.require(Algorithm.HMAC512("secret"))
                .withIssuer(APP_NAME)
                .withClaimPresence("user")
                .build();
        return verifier.verify(token) != null;
    }

    public static String extractEmailFrom(String token){
        var claim = JWT.decode(token).getClaim("user");
        return (String) claim.asMap().get("user");
    }
}
