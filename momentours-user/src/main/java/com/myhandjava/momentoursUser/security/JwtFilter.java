package com.myhandjava.momentoursUser.security;

import com.myhandjava.momentoursUser.query.service.UserQueryService;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j

public class JwtFilter extends OncePerRequestFilter {

    private final UserQueryService userQueryService;
    private final JwtUtil jwtUtil;

    public JwtFilter(UserQueryService userQueryService, JwtUtil jwtUtil) {
        this.userQueryService = userQueryService;
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7);
            if (jwtUtil.validateToken(token)) {
                Authentication authentication = jwtUtil.getAuthentication(token);
                SecurityContextHolder.getContext().setAuthentication(authentication);
                // Claims를 요청 속성에 추가
                Claims claims = jwtUtil.parseClaims(token);
                request.setAttribute("claims", claims);
            }
        }

        filterChain.doFilter(request, response);
    }

}






