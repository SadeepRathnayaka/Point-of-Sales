package com.point_of_sales.order_service.advisor;

import com.point_of_sales.customer_service.exception.NotFoundException;
import com.point_of_sales.customer_service.utils.StandardResponse;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Hidden
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<StandardResponse> handleNotFoundException(NotFoundException ex){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).
                body(new StandardResponse(404, "BAD_REQUEST", ex.getMessage()));
    }
}
