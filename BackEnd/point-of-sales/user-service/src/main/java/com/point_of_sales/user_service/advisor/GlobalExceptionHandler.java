package com.point_of_sales.user_service.advisor;

import com.point_of_sales.user_service.exception.AlreadyExistsException;
import com.point_of_sales.user_service.exception.MisMatchException;
import com.point_of_sales.user_service.exception.PermissionDeniedException;
import com.point_of_sales.user_service.utils.StandardResponse;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Hidden
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MisMatchException.class)
    public ResponseEntity<StandardResponse> handleMisMatchException(MisMatchException ex){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).
                body(new StandardResponse(403, "BAD_REQUEST", ex.getMessage()));
    }

    @ExceptionHandler(AlreadyExistsException.class)
    public ResponseEntity<StandardResponse> handleAlreadyExistsException(AlreadyExistsException ex){
        return ResponseEntity.status(HttpStatus.CONFLICT).
                body(new StandardResponse(409, "CONFLICT", ex.getMessage()));
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<StandardResponse> handleUsernameNotFoundException(UsernameNotFoundException ex){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).
                body(new StandardResponse(404, "NOT_FOUND", ex.getMessage()));
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<StandardResponse> handleBadCredentialsException(BadCredentialsException ex){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).
                body(new StandardResponse(403, "BAD_REQUEST", ex.getMessage()));
    }

    @ExceptionHandler(PermissionDeniedException.class)
    public ResponseEntity<StandardResponse> handlePermissionDeniedException(PermissionDeniedException ex){
        return ResponseEntity.status(HttpStatus.FORBIDDEN).
                body(new StandardResponse(403, "BAD_REQUEST", ex.getMessage()));
    }


}
