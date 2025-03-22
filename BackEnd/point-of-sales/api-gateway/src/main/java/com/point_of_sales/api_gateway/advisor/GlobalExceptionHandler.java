package com.point_of_sales.api_gateway.advisor;

import com.point_of_sales.api_gateway.exception.AuthenticationException;
import com.point_of_sales.api_gateway.utils.StandardResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<StandardResponse> handleAuthenticationException(AuthenticationException ex){
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).
                body(new StandardResponse(401, "UNAUTHORIZED", ex.getMessage()));
    }
}
