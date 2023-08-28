package com.legends.promiscuous.security.provider;

import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collection;

import static com.legends.promiscuous.exceptions.ExceptionMessage.INVALID_CREDENTIALS_EXCEPTION;

@Component
@AllArgsConstructor
public class PromiscuousAuthenticationProvider implements AuthenticationProvider {
    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        //1. take the username from the request(contained in the authentication object) and use
        //the userDetailsService to look for a user from the DB with that username
        String email = authentication.getPrincipal().toString();
        UserDetails user = userDetailsService.loadUserByUsername(email);

        //2. if user from 1. is found, use the PasswordEncoder to compare the password from the
        //request with the password from the DB

        String password = authentication.getCredentials().toString();
        boolean isValidPasswordMatch = passwordEncoder.matches(password, user.getPassword());
        //3. if the passwords match, request is authenticated
        if(isValidPasswordMatch) {
             Collection<? extends GrantedAuthority> authorities =  user.getAuthorities();
             Authentication authenticationResult = new UsernamePasswordAuthenticationToken(email,password,authorities);
            //return Authentication object with user authority
            return authenticationResult;
        }

        //4. if the passwords don't match, request is not authenticated'
        throw new BadCredentialsException(INVALID_CREDENTIALS_EXCEPTION.getMessage());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
