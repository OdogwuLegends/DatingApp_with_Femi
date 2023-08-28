package com.legends.promiscuous.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.security.core.GrantedAuthority;

import java.time.Instant;
import java.util.Collection;
import java.util.List;

import static com.legends.promiscuous.utils.AppUtil.APP_NAME;

public class JwtUtil {
    public static String generateVerificationToken(String email){
        // generate token that has the user's email embedded in it
        //TODO: Refactor this, remove hardcoded values
        return  JWT.create()
                .withClaim("user", email)
                .withIssuer(APP_NAME)
                .withExpiresAt(Instant.now().plusSeconds(3600))
                .sign(Algorithm.HMAC512("secret"));

    }

    public static String generateAccessToken(List<? extends GrantedAuthority> authorities){
        return  JWT.create()
                .withClaim("roles", authorities)
                .withIssuer(APP_NAME)
                .withExpiresAt(Instant.now().plusSeconds(3600 * 24))
                .sign(Algorithm.HMAC512("secret"));

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
