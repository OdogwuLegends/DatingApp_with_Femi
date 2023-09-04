package com.legends.promiscuous.security.filters;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;
import static com.legends.promiscuous.utils.AppUtil.APP_NAME;
import static com.legends.promiscuous.utils.AppUtil.getPublicPaths;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

public class PromiscuousAuthorizationFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if(getPublicPaths().contains(request.getServletPath())) filterChain.doFilter(request,response);
        else {
            String authorizationHeader = request.getHeader(AUTHORIZATION); // this holds the bearer and the authorization token "Bearer xxxxxxxxx"
            // collect all the characters after the bearer and the space after it which is the "authorization token"
            String authorizationToken = authorizationHeader.substring("Bearer ".length());

            JWTVerifier verifier = JWT.require(HMAC512("secret"))
                    .withIssuer(APP_NAME)
                    .withClaimPresence("roles")
                    .build();
            DecodedJWT decodedToken = verifier.verify(authorizationToken);
            List<SimpleGrantedAuthority> authorities = decodedToken.getClaim("roles").asList(SimpleGrantedAuthority.class);

            Authentication authentication = new UsernamePasswordAuthenticationToken(null,null,authorities);
            SecurityContextHolder.getContext().setAuthentication(authentication);

            filterChain.doFilter(request,response);
        }
    }
}
