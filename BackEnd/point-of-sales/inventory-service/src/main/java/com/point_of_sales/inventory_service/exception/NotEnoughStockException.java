package com.point_of_sales.inventory_service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class NotEnoughStockException extends RuntimeException {
    public NotEnoughStockException(String message){
        super(message);
    }
}
