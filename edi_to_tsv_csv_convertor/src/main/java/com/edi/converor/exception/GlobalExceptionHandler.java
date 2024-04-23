package com.edi.converor.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomRuntimeException.class)
    public ResponseEntity<String> CustomRuntimeException(){
            return new ResponseEntity<>("Internal error.",HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(InvalidFileException.class)
    public ResponseEntity<String> CustomIOException(){
        return new ResponseEntity<>("File is empty, Please try again.",HttpStatus.BAD_REQUEST);
    }
}
