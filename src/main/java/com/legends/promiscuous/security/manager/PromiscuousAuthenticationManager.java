package com.legends.promiscuous.security.manager;

import com.legends.promiscuous.exceptions.AuthenticationNotSupportedException;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class PromiscuousAuthenticationManager implements AuthenticationManager {
    private final AuthenticationProvider authenticationProvider;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        if(authenticationProvider.supports(authentication.getClass())){
        return authenticationProvider.authenticate(authentication);
        }
        throw new AuthenticationNotSupportedException("Authentication not supported on this system");
    }
}
