package com.legends.promiscuous.utils;

import com.legends.promiscuous.config.AppConfig;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@SpringBootTest
class AppUtilTest {
    @Autowired
    private AppConfig appConfig;
    @Test
    public void testGenerateActivationLink(){
        String activationLink =  AppUtil.generateActivationLink(appConfig.getBaseUrl(),"test@gmail.com");
//        String activationLink =  AppUtil.generateActivationLink("localhost:8080","test@gmail.com");
        log.info("activation link -> {} ", activationLink);
        assertThat(activationLink).isNotNull();
        assertThat(activationLink).contains("localhost:8080/user/activate?code=");
    }

    @Test
    public void generateTokenTest(){
        String email = "test@gmail.com";
        String token = JwtUtil.generateToken(email);
        log.info("generated token ->{}",token);
        assertThat(token).isNotNull();
    }
}