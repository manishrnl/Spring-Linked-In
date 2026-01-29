package com.example.api_gateway.filters;

import com.example.api_gateway.services.JwtService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class AuthenticationGatewayFilterFactory extends AbstractGatewayFilterFactory<AuthenticationGatewayFilterFactory.Config> {

    private final JwtService jwtService;

    public AuthenticationGatewayFilterFactory(JwtService jwtService) {
        super(Config.class);
        this.jwtService = jwtService;
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {

            if (!config.isEnabled()) {
                log.error("Authentication filter is disabled , Please enable it in AuthenticationFilter as isEnable = true ");
                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                return exchange.getResponse().setComplete();
            }

            String authorizationHeader = exchange.getRequest().getHeaders().getFirst("Authorization");
            if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                return exchange.getResponse().setComplete();
            }


            String token = authorizationHeader.split("Bearer ")[1];
            String userId = jwtService.getUserIdFromToken(token);

            if (userId == null) {
                log.error("Authentication failed: User ID could not be extracted from token");
                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                return exchange.getResponse().setComplete();
            }

            log.info("Successfully authenticated User ID: {}. Forwarding to service...", userId);
//            Why Code Fails
//            This creates a NEW request object in memory, but does nothing with it
//            exchange.getRequest()
//                    .mutate()
//                    .header("X-User-Id", userId)
//                    .build();

// You are passing the ORIGINAL exchange (which contains the ORIGINAL request)
//            return chain.filter(exchange);


            // --- THE CRITICAL FIX IS HERE ---
            // 1. Mutate the exchange
            // 2. Provide a lambda to mutate the request within that exchange
            // 3. Return the filter call with the NEW exchange
            return chain.filter(
                    exchange.mutate()
                            .request(builder -> builder.header("X-User-Id", userId))
                            .build()
            );
        };
    }

    @Data
    public static class Config {
        private boolean enabled;
    }
}
