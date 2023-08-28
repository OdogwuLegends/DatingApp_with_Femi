package com.legends.promiscuous.security;

import com.legends.promiscuous.security.filters.PromiscuousAuthenticationFilter;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import static com.legends.promiscuous.enums.Role.CUSTOMER;

@Configuration
@AllArgsConstructor
public class SecurityConfig {
    private final AuthenticationManager authenticationManager;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{
        return httpSecurity
                .addFilterAt(new PromiscuousAuthenticationFilter(authenticationManager), BasicAuthenticationFilter.class)
                .authorizeHttpRequests(customizer-> customizer.requestMatchers(HttpMethod.POST,"/api/v1/user").permitAll())
                .authorizeHttpRequests(customizer-> customizer.requestMatchers(HttpMethod.POST,"/api/v1/user/uploadMedia")
                        .hasRole(CUSTOMER.name()))
                .authorizeHttpRequests(customizer-> customizer.requestMatchers(HttpMethod.PUT,"/api/v1/user/**")
                        .hasRole(CUSTOMER.name()))
                .build();
    }
}
