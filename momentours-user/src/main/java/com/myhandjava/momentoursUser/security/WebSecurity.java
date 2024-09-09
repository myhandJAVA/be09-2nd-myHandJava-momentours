package com.myhandjava.momentoursUser.security;

import com.myhandjava.momentoursUser.query.repository.UserMapper;
import com.myhandjava.momentoursUser.query.service.UserQueryService;
import jakarta.servlet.Filter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class WebSecurity {

    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private UserQueryService userQueryService;
    private Environment env;
    private JwtUtil jwtUtil;
    private UserMapper userMapper;

    @Autowired
    public WebSecurity(BCryptPasswordEncoder bCryptPasswordEncoder
                        , UserQueryService userQueryService
                        , Environment env
                        , JwtUtil jwtUtil
                        , UserMapper userMapper) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userQueryService = userQueryService;
        this.env = env;
        this.jwtUtil = jwtUtil;
        this.userMapper = userMapper;
    }

    @Bean
    protected SecurityFilterChain configure(HttpSecurity http) throws Exception {

        http.csrf((csrf) -> csrf.disable());

        AuthenticationManagerBuilder authenticationManagerBuilder =
                http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(userQueryService)
                .passwordEncoder(bCryptPasswordEncoder);

        AuthenticationManager authenticationManager = authenticationManagerBuilder.build();
        
        http.authorizeHttpRequests((authz) ->
                authz.requestMatchers(new AntPathRequestMatcher("/users/**", "POST")).permitAll()
                     .anyRequest().authenticated()
        )
                .authenticationManager(authenticationManager)

                .sessionManagement((session) -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        http.addFilter(getAuthenticationFilter(authenticationManager));
        http.addFilterBefore(new JwtFilter(userQueryService, jwtUtil), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    private Filter getAuthenticationFilter(AuthenticationManager authenticationManager) {
        return new AuthenticationFilter(authenticationManager, userQueryService, env,userMapper);
    }

}
