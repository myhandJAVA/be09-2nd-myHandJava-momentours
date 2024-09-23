package com.myhandjava.momentours.config;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Configuration
public class UserFeignClientConfig {

    @Bean(name="UserFeignClientRequestInterceptor")
    public RequestInterceptor requestInterceptor() {
        return new RequestInterceptor() {
            @Override
            public void apply(RequestTemplate requestTemplate) {

                ServletRequestAttributes requestAttributes =
                        (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

                if(requestAttributes != null) {

                    String authorizationHeader = requestAttributes
                            .getRequest()
                            .getHeader(HttpHeaders.AUTHORIZATION);

                    if(authorizationHeader != null) {

                        requestTemplate.header(HttpHeaders.AUTHORIZATION, authorizationHeader);
                    }
                }

                }
            };
        }
}

