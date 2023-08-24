package com.legends.promiscuous.config;

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

    @Value("${cloud.api.secret}")
    private String cloudSecret;

    @Value("${cloud.api.key}")
    private String cloudKey;

    @Value("${cloud.api.name}")
    private String cloudName;

    public String getMailApiKey() {
        return mailApiKey;
    }

    public String getTestToken() {
        return testToken;
    }

    public String getBaseUrl(){
        return baseUrl;
    }

    public String getCloudSecret() {
        return cloudSecret;
    }

    public String getCloudKey() {
        return cloudKey;
    }

    public String getCloudName() {
        return cloudName;
    }
}
