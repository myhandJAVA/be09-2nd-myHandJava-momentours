package com.myhandjava.momentoursUser.security;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.myhandjava.momentoursUser.command.domain.aggregate.UserEntity;
import com.myhandjava.momentoursUser.query.repository.UserMapper;
import com.myhandjava.momentoursUser.query.service.UserQueryService;
import com.myhandjava.momentoursUser.command.domain.vo.RequestLoginVO;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


@Slf4j
public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private UserQueryService userSerivce;
    private Environment env;
    private UserQueryService userQueryService;
    private UserMapper userMapper;

    public AuthenticationFilter(AuthenticationManager authenticationManager,
                                UserQueryService userSerivce,
                                Environment env,
                                UserMapper userMapper) {
        super(authenticationManager);
        this.userSerivce = userSerivce;
        this.env = env;
        this.userMapper = userMapper;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        try {
            RequestLoginVO creds = new ObjectMapper().readValue(request.getInputStream(), RequestLoginVO.class);

            return getAuthenticationManager().authenticate(
                    new UsernamePasswordAuthenticationToken(creds.getUserEmail(), creds.getUserPwd(), new ArrayList<>())
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {



        String userName = ((User)authResult.getPrincipal()).getUsername();
        UserEntity userEntity = userMapper.findByUserEmail(userName);
        int userNo = userEntity.getUserNo();
        Integer coupleNo = userMapper.findUserCoupleNoByUserNo(userNo);



        List<String> roles = authResult.getAuthorities().stream()
                            .map(role -> role.getAuthority())
                            .collect(Collectors.toList());
        
        Claims claims = Jwts.claims().setSubject(userName);
        claims.put("auth", roles);
        claims.put("userNo",userNo);
        claims.put("coupleNo",coupleNo);

        String token = Jwts.builder()
                .setClaims(claims)
                .setExpiration(new Date(System.currentTimeMillis()
                        + Long.parseLong(env.getProperty("token.expiration_time"))))
                .signWith(SignatureAlgorithm.HS512, env.getProperty("token.secret"))
                .compact();

        response.addHeader("token", token);
    }
}













