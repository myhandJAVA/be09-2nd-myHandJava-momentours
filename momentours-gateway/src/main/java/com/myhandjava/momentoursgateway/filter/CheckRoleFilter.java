package com.myhandjava.momentoursgateway.filter;

import lombok.Getter;
import lombok.Setter;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.env.Environment;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.List;
@Component
public class CheckRoleFilter extends AbstractGatewayFilterFactory<CheckRoleFilter.Config> {

    Environment env;

    public CheckRoleFilter(Environment env) {
        super(Config.class);
        this.env = env;
    }

    @Getter
    @Setter
    public static class Config{
        private List<String> roleList;
    }

    private Mono<Void> onError(ServerWebExchange exchange, String errorMessage, HttpStatus httpStatus) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(httpStatus);
        response.getHeaders().add("Content-Type","application/json");
        DataBufferFactory dataBufferFactory = response.bufferFactory();
        DataBuffer dataBuffer = dataBufferFactory.wrap(errorMessage.getBytes(StandardCharsets.UTF_8));

        return response.writeWith(Mono.just(dataBuffer));
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            HttpHeaders headers = request.getHeaders();
            String role = headers.get(HttpHeaders.AUTHORIZATION).get(0);
            List<String> requiredRoles = config.getRoleList();

            if(!requiredRoles.contains(role)){
                return onError(exchange, "해당 요청에 필요한 권한이 없습니다.", HttpStatus.UNAUTHORIZED);
            }
            return chain.filter(exchange);
        };

    }


}
