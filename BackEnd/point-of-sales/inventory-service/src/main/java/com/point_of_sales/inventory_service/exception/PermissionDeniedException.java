package com.point_of_sales.inventory_service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class PermissionDeniedException extends RuntimeException{
    public PermissionDeniedException(String message){
        super(message);
    }
}
