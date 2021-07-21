package com.nashtech.ecommerce_webapp.exceptions;

import org.springframework.core.convert.ConversionFailedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpServerErrorException;

@ControllerAdvice
public class GlobalControllerExceptionHandler {
    //convert string to enum exception
    @ExceptionHandler(ConversionFailedException.class)
    public ResponseEntity<String> handleConflict(RuntimeException ex){
        return new ResponseEntity<>("failed to convert: "+ex.getMessage(), HttpStatus.BAD_REQUEST);
    }
    //internal server error exception
    @ExceptionHandler(HttpServerErrorException.InternalServerError.class)
    public ResponseEntity<String> handleInternalServerError(RuntimeException ex){
        return new ResponseEntity<>("an internal error has occurred: "+ex.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
