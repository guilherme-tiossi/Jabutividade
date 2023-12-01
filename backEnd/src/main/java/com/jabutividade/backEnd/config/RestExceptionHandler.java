package com.jabutividade.backEnd.config;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jabutividade.backEnd.dto.ErrorDto;
import com.jabutividade.backEnd.exceptions.AppException;

@ControllerAdvice
public class RestExceptionHandler {
    
    @ExceptionHandler(value = { AppException.class })
    @ResponseBody
    public ResponseEntity<ErrorDto> handleException(AppException ex) {
        return ResponseEntity.status(ex.geHttpStatus())
            .body(new ErrorDto(ex.getMessage()));
    }

}
