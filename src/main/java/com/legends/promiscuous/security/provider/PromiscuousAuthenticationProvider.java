package com.legends.promiscuous.security.provider;

import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class PromiscuousAuthenticationProvider implements AuthenticationProvider {

    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;





    //3. if the passwords match, request is authenticated

    //4. if the passwords don't match, request is not authenticated'
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        //1. take the username from the request(contained in the authentication object) and use
        //the userDetailsService to look for a user from the DB with that username
        String email = authentication.getPrincipal().toString();
        UserDetails user = userDetailsService.loadUserByUsername(email);

        //2. if user from 1. is found, use the PasswordEncoder to compare the password from the
        //request with the password from the DB

        String password = authentication.getCredentials().toString();
        return null;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return false;
    }
}
