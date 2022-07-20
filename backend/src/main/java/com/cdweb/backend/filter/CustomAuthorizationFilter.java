package com.cdweb.backend.filter;

import com.cdweb.backend.common.Constant;
import com.cdweb.backend.common.JwtService;
import com.cdweb.backend.entities.Users;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

//@RequiredArgsConstructor
@Slf4j
public class CustomAuthorizationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtService jwtService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (request.getServletPath().equals("/api/v1/auth/login") || request.getServletPath().equals("/api/v1/auth/admin/login")
                || request.getServletPath().equals("/api/v1/auth/logout") || request.getServletPath().contains("no-token")
                || request.getServletPath().equals("/api/v1/auth/refresh-token")) {
            filterChain.doFilter(request, response);
        } else {
            String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
            log.info("token{}", authorizationHeader);
            if (authorizationHeader != null && authorizationHeader.startsWith(Constant.BEARER)) {
                try {
                    String token = authorizationHeader.substring(Constant.BEARER.length());
                    Users appUser = jwtService.getUserFromToken(token);
                    UsernamePasswordAuthenticationToken authenticationToken =
                            new UsernamePasswordAuthenticationToken(appUser.getUsername(), null,
                                    Collections.singleton(new SimpleGrantedAuthority(appUser.getRoles().getRoleCode())));
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                    filterChain.doFilter(request, response);
                } catch (Exception exception) {
                    log.error("Error request: {}", exception.getMessage());
                    response.setHeader("error", exception.getMessage());
                    response.setStatus(HttpStatus.FORBIDDEN.value());
                    response.setContentType(APPLICATION_JSON_VALUE);
                    Map<String, String> error = new HashMap<>();
                    error.put("error", exception.getMessage());
                    new ObjectMapper().writeValue(response.getOutputStream(), error);
                }
            } else {
                filterChain.doFilter(request, response);
            }
        }
    }
}