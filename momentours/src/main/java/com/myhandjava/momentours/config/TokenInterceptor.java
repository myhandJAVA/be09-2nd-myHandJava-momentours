package com.myhandjava.momentours.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.HandlerInterceptor;

@Configuration
@Slf4j
public class TokenInterceptor implements HandlerInterceptor {
    private Environment env;

    @Autowired
    public TokenInterceptor(Environment env){
        this.env = env;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7);

            Jws<Claims> claims = Jwts.parserBuilder()
                    .setSigningKey(env.getProperty("token.secret"))
                    .build()
                    .parseClaimsJws(token);

            request.setAttribute("claims", claims.getBody());
        }

        return true;
    }
}

