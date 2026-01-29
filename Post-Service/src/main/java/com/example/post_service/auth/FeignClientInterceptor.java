package com.example.post_service.auth;

import feign.RequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignClientInterceptor {

    @Bean
    public RequestInterceptor requestInterceptor() {
        return requestTemplate -> {
            // 1. Retrieve the User ID from the current thread's context.
            // This was likely populated by a WebFilter or Interceptor when the request entered this service.
            Long userId = UserContextHolder.getCurrentUserId();

            // 2. Check if the User ID exists to avoid attaching 'null' strings to headers.
            if (userId != null) {
                // 3. Manually inject the 'X-User-Id' header into the outgoing Feign request.
                // This allows the downstream service to know which user is performing the action.
                requestTemplate.header("X-User-Id", userId.toString());
            }
        };
    }
}