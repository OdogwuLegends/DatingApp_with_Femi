package com.legends.promiscuous.security.services;

import com.legends.promiscuous.models.User;
import com.legends.promiscuous.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@AllArgsConstructor
public class PromiscuousUserDetailsService implements UserDetailsService {
    private final UserService userService;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userService.getUserByUsername(username);
        return null;
    }
}
