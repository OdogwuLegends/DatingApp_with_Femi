package com.legends.promiscuous.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    @Value("${mail.api.key}")
    private String mailApiKey;
    @Value("${app.base.url}")
    private String baseUrl;

    @Value("${app.dev.token}")
    private String testToken;

    public String getMailApiKey() {
        return mailApiKey;
    }

    public String getTestToken() {
        return testToken;
    }

    public String getBaseUrl(){
        return baseUrl; }
}
