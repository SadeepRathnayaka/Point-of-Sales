package com.point_of_sales.inventory_service.advisor;

import com.point_of_sales.inventory_service.exception.NotEnoughStockException;
import com.point_of_sales.inventory_service.exception.NotFoundException;
import com.point_of_sales.inventory_service.exception.PermissionDeniedException;
import com.point_of_sales.inventory_service.utils.StandardResponse;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Hidden
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(PermissionDeniedException.class)
    public ResponseEntity<StandardResponse> handlePermissionDeniedException(PermissionDeniedException ex){
        return ResponseEntity.status(HttpStatus.FORBIDDEN).
                body(new StandardResponse(403, "BAD_REQUEST", ex.getMessage()));
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<StandardResponse> handleNotFoundException(NotFoundException ex){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).
                body(new StandardResponse(404, "BAD_REQUEST", ex.getMessage()));
    }

    @ExceptionHandler(NotEnoughStockException.class)
    public ResponseEntity<StandardResponse> handleNotEnoughStockException(NotEnoughStockException ex){
        return ResponseEntity.status(HttpStatus.CONFLICT).
                body(new StandardResponse(409, "BAD_REQUEST", ex.getMessage()));
    }




}
