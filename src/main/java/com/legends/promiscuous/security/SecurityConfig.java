package com.legends.promiscuous.security;

import com.legends.promiscuous.security.filters.PromiscuousAuthenticationFilter;
import com.legends.promiscuous.security.filters.PromiscuousAuthorizationFilter;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static com.legends.promiscuous.enums.Role.CUSTOMER;

@Configuration
@AllArgsConstructor
public class SecurityConfig {
    private final AuthenticationManager authenticationManager;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{
        final String[] publicEndPoints = {"/api/v1/user/register", "/login"};
        return httpSecurity
                .addFilterAt(new PromiscuousAuthenticationFilter(authenticationManager), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(new PromiscuousAuthorizationFilter(), PromiscuousAuthenticationFilter.class)
                .sessionManagement(customizer->customizer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .csrf(c->c.disable())
                .cors(Customizer.withDefaults())
                .authorizeHttpRequests(c->c.requestMatchers(HttpMethod.POST, publicEndPoints).permitAll())
//                .authorizeHttpRequests(c->c.requestMatchers(HttpMethod.PUT, "/api/v1/user/**").hasAnyAuthority(CUSTOMER.name()))
//                .authorizeHttpRequests(c->c.requestMatchers(HttpMethod.GET, "/api/v1/user/**").hasAnyAuthority(CUSTOMER.name()))
                .authorizeHttpRequests(c->c.anyRequest().hasAuthority(CUSTOMER.name()))
                .build();
    }
}
