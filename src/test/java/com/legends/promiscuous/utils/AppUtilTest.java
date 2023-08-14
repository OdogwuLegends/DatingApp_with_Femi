package com.legends.promiscuous.utils;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
class AppUtilTest {
    @Test
    public void testGenerateActivationLink(){
        String activationLink =  AppUtil.generateActivationLink("test@gmail.com");
        log.info("activation link -> {} ", activationLink);
        assertThat(activationLink).isNotNull();
        assertThat(activationLink).contains("http://localhost:8080/activate?code=");
    }

    @Test
    public void generateTokenTest(){
        String email = "test@gmail.com";
        String token = JwtUtil.generateToken(email);
        log.info("generated token ->{}",token);
        assertThat(token).isNotNull();
    }
}