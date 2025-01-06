package com.example.forumservice.exception;

import com.example.forumservice.dto.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ApiResponse<Object>> handleBadRequestException(HttpServletRequest request,
                                                                         HttpServletResponse response,
                                                                         BadRequestException e) {
        /* TODO: Add Exception log to table */

        ApiResponse<Object> exception = new ApiResponse<>(HttpStatus.BAD_REQUEST.name(), e.getMessage());
        return new ResponseEntity<>(exception, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InternalServerException.class)
    public ResponseEntity<ApiResponse<Object>> handleInternalServerException(HttpServletRequest request,
                                                                             HttpServletResponse response,
                                                                             InternalServerException e) {


        /* TODO: Add Exception log to table */

        ApiResponse<Object> exception = new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.name(), e.getMessage());
        return new ResponseEntity<>(exception, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
