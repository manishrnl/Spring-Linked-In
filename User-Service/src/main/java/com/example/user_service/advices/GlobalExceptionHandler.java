package com.example.user_service.advices;

import com.example.user_service.exceptions.BadRequestException;
import com.example.user_service.exceptions.ResourceNotFoundException;
import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.servlet.view.InternalResourceViewResolver;


import javax.naming.AuthenticationException;
import java.nio.file.AccessDeniedException;

@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private final InternalResourceViewResolver internalResourceViewResolver;

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse<?>> handleResourceNotFound(ResourceNotFoundException exceptions) {
        ApiError apiError = new ApiError(exceptions.getLocalizedMessage(), HttpStatus.NOT_FOUND);
        ApiResponse<?> apiResponse = new ApiResponse<>(apiError);
        return new ResponseEntity<>(apiResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ApiResponse<?>> handleBadRequestException(BadRequestException badRequestException) {
        ApiError apiError = ApiError.builder()
                .message(badRequestException.getMessage())
                .status(HttpStatus.BAD_REQUEST)
                .build();
        ApiResponse<?> apiResponse = new ApiResponse<>(apiError);
        return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpServerErrorException.InternalServerError.class)
    public ResponseEntity<ApiResponse<?>> handleInternalServerError(HttpServerErrorException.InternalServerError exception) {
        ApiError apiError = new ApiError(exception.getMessage(),
                HttpStatus.INTERNAL_SERVER_ERROR);
        ApiResponse<?> apiResponse = new ApiResponse<>(apiError);
        return new ResponseEntity<>(apiResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }




    @ExceptionHandler(JwtException.class)
    public ResponseEntity<ApiResponse<?>> handleJwtException(JwtException exception) {
        ApiError apiError = new ApiError(exception.getLocalizedMessage(), HttpStatus.UNAUTHORIZED);
        ApiResponse<?> apiResponse = new ApiResponse<>(apiError);
        return new ResponseEntity<>(apiResponse, HttpStatus.UNAUTHORIZED);
    }


    //    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<?>> handleInternalServerError(Exception exception) {
        ApiError apiError = ApiError.builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .message(exception.getMessage())
                .build();
        return buildErrorResponseEntity(apiError);
    }


    //  @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ApiResponse<?>> handleAuthenticationException(AuthenticationException exception) {
        ApiError apiError = new ApiError(exception.getLocalizedMessage(), HttpStatus.UNAUTHORIZED);
        ApiResponse<?> apiResponse = new ApiResponse<>(apiError);
        return new ResponseEntity<>(apiResponse, HttpStatus.UNAUTHORIZED);
    }


    //  @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiResponse<?>> handleAccessDeniedException(AccessDeniedException exception) {
        ApiError apiError = ApiError.builder().build();
        ApiResponse<?> apiResponse = new ApiResponse<>(apiError);
        return new ResponseEntity<>(apiResponse, HttpStatus.FORBIDDEN);
    }

    private ResponseEntity<ApiResponse<?>> buildErrorResponseEntity(ApiError apiError) {
        return new ResponseEntity<>(new ApiResponse<>(apiError), apiError.getStatus());
    }
}
