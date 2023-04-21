package com.example.hostelAvailabilitySystem.security.utils;

import com.example.hostelAvailabilitySystem.model.AppUser;
import com.example.hostelAvailabilitySystem.repository.AppUserRepository;
import com.example.hostelAvailabilitySystem.security.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.http.HttpHeaders;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private AppUserRepository appUserRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        // Get authorization header and validate
        //final String header = request.getHeader(AUTHORIZATION);
        final String header = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (!StringUtils.hasText(header) || (StringUtils.hasText(header) && !header.startsWith("Bearer "))) {
            filterChain.doFilter(request, response);
            return;
        }

        // Authorization -> ([Bearer], [asdf.wcejvce.efcekciekbn])
        final String token = header.split(" ")[1].trim();

        // Get User identity and set it on the spring security context
        AppUser appUser = appUserRepository
                .findByEmail(jwtUtil.getUsernameFromToken(token))
                .orElse(null);

        // Get jwt token and validate
        if (!jwtUtil.validateToken(token,appUser)) {
            filterChain.doFilter(request, response);
            return;
        }

        UsernamePasswordAuthenticationToken
                authenticationToken = new UsernamePasswordAuthenticationToken(
                        appUser,null, appUser == null ? List.of() : appUser.getAuthorities()
        );

        authenticationToken.setDetails(
                new WebAuthenticationDetailsSource().buildDetails(request)
        );

        // this is where the authentication magic happens and the user is now valid!
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        filterChain.doFilter(request, response);

    }
}
