package com.myhandjava.momentours.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.Collection;

@OpenAPIDefinition(
    info = @Info(
            title = "Momentours 프로젝트 API 명세서",
            description = "Momentours 프로젝트에 사용되는 API 명세서",
            version = "v1"))
@Configuration
@RequiredArgsConstructor
public class SwaggerConfig {

    @Bean
    // 운영 환경에는 Swagger를 비활성화하기 위해 추가
    @Profile("!Prod")
    public GroupedOpenApi diaryOpenAPI() {

        String[] paths = {"/diary/**"};

        return GroupedOpenApi
                .builder()
                .group("일기 관련 API")
                .pathsToMatch(paths)
                .addOpenApiCustomizer(buildSecurityOpenApi()).build();
    }

    @Bean
    @Profile("!Prod")
    public GroupedOpenApi momentOpenAPI() {

        String[] paths = {"/moment/**"};

        return GroupedOpenApi
                .builder()
                .group("추억 관련 API")
                .pathsToMatch(paths)
                .addOpenApiCustomizer(buildSecurityOpenApi()).build();
    }

    @Bean
    @Profile("!Prod")
    public GroupedOpenApi momentcourseOpenAPI() {

        String[] paths = {"/momentcourse/**"};

        return GroupedOpenApi
                .builder()
                .group("추억코스 관련 API")
                .pathsToMatch(paths)
                .addOpenApiCustomizer(buildSecurityOpenApi()).build();
    }

    @Bean
    @Profile("!Prod")
    public GroupedOpenApi randomquestionOpenAPI() {

        String[] paths = {"/randomquestion/**"};

        return GroupedOpenApi
                .builder()
                .group("랜덤질문 관련 API")
                .pathsToMatch(paths)
                .addOpenApiCustomizer(buildSecurityOpenApi()).build();
    }

    @Bean
    @Profile("!Prod")
    public GroupedOpenApi calendarOpenAPI() {

        String[] paths = {"/calendar/**"};

        return GroupedOpenApi
                .builder()
                .group("캘린더 관련 API")
                .pathsToMatch(paths)
                .addOpenApiCustomizer(buildSecurityOpenApi()).build();
    }

    @Bean
    @Profile("!Prod")
    public GroupedOpenApi todocourseOpenAPI() {

        String[] paths = {"/todocourse/**"};

        return GroupedOpenApi
                .builder()
                .group("예정코스 관련 API")
                .pathsToMatch(paths)
                .addOpenApiCustomizer(buildSecurityOpenApi()).build();
    }

    @Bean
    @Profile("!Prod")
    public GroupedOpenApi coupleOpenAPI() {

        String[] paths = {"/couple/**"};

        return GroupedOpenApi
                .builder()
                .group("커플 관련 API")
                .pathsToMatch(paths)
                .addOpenApiCustomizer(buildSecurityOpenApi()).build();
    }

    public OpenApiCustomizer buildSecurityOpenApi() {
        // jwt token을 한번 설정하면 header에 값을 넣어주는 코드
        return OpenApi -> OpenApi.addSecurityItem(new SecurityRequirement().addList("jwt token"))
                .getComponents().addSecuritySchemes("jwt token", new SecurityScheme()
                        .name("Authorization")
                        .type(SecurityScheme.Type.HTTP)
                        .in(SecurityScheme.In.HEADER)
                        .bearerFormat("JWT")
                        .scheme("bearer"));
    }


}
