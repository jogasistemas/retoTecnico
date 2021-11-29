package com.exchange.reactive.web;

import com.exchange.reactive.exception.ErrorCode;
import com.exchange.reactive.webdto.response.BaseWebResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.persistence.EntityNotFoundException;

@RestControllerAdvice
public class ExceptionRestController {
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<BaseWebResponse> handleEntityNotFoundException() {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(BaseWebResponse.error(ErrorCode.ENTITY_NOT_FOUND));
    }
}
