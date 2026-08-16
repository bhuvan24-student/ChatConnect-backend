package com.chatconnect.exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<String> usernotfoundhandle(UserNotFoundException ex){
        return ResponseEntity.status(404).body(ex.getMessage());
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> methodargumentnotvalid( MethodArgumentNotValidException ex){
        Map<String,String>errormessages=new HashMap<>();
        for(FieldError error:ex.getBindingResult().getFieldErrors()){
            errormessages.put(error.getField(),error.getDefaultMessage());
        }
        return ResponseEntity.status(400).body(errormessages);
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> unknowexceptionhandler(Exception ex){
        return ResponseEntity.status(500).body("Something went wrong");
    }
}
