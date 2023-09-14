package dev.tylercash.takehome.interview687e214f.controller;

import dev.tylercash.takehome.interview687e214f.exceptions.ApiException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ExceptionHandlerController extends ResponseEntityExceptionHandler {
    @ExceptionHandler({ApiException.class})
    protected ResponseEntity<Object> handleConflict(ApiException ex) {
        String body = "Internal server error";
        if (ex.getHttpStatusCode().is4xxClientError()){
            body = ex.getMessage();
        }
        return new ResponseEntity<>(body, new HttpHeaders(), HttpStatus.valueOf(ex.getHttpStatusCode().value()));
    }
}
