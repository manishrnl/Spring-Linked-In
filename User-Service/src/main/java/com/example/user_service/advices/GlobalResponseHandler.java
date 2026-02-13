package com.example.user_service.advices;

import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;
import java.util.List;

@RestControllerAdvice
public class GlobalResponseHandler implements ResponseBodyAdvice<Object> {

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body,
                                  MethodParameter returnType,
                                  MediaType selectedContentType,
                                  Class<? extends HttpMessageConverter<?>> selectedConverterType,
                                  ServerHttpRequest request,
                                  ServerHttpResponse response) {

        // ✅ 1. Skip wrapping for Swagger/Actuator endpoints (your logic)
        List<String> allowedRoutes = List.of("/v3/api-docs", "/actuator");
        boolean isAllowed = allowedRoutes.stream()
                .anyMatch(route -> request.getURI().getPath().contains(route));

        if (isAllowed)
            return body;

        // ✅ 2. Get HTTP status
        HttpStatus status = HttpStatus.resolve(
                ((ServletServerHttpResponse) response).getServletResponse().getStatus()
        );

        // ✅ 3. Skip wrapping for:
        // - No Content (204)
        // - Not Found (404)
        // - Internal Server Error (500)
        // - Or when body is null
        if (status == HttpStatus.NO_CONTENT ||
                status == HttpStatus.NOT_FOUND ||
                status == HttpStatus.INTERNAL_SERVER_ERROR ||
                body == null) {
            return body;
        }

        // ✅ 4. Skip wrapping if it's already an ApiResponse
        if (body instanceof ApiResponse<?>) {
            return body;
        }

        // ✅ 5. Skip wrapping if controller explicitly returns ResponseEntity (avoid cast issue)
        if (ResponseEntity.class.isAssignableFrom(returnType.getParameterType())) {
            return body;
        }

        // ✅ 6. Finally wrap
        return new ApiResponse<>(body);
    }

}

