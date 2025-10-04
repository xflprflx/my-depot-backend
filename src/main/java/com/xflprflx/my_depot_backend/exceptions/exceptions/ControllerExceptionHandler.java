package com.xflprflx.my_depot_backend.exceptions.exceptions;


import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;

import java.net.URI;
import java.time.Instant;
import java.util.Locale;

@RestControllerAdvice
public class ControllerExceptionHandler {

    private final MessageSource messageSource;

    public ControllerExceptionHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ExceptionHandler(HttpClientErrorException.class)
    private ResponseEntity<ProblemDetail> handleHttpClientErrorException(HttpClientErrorException e, HttpServletRequest request, Locale locale) {
        System.out.println("EXCEPTION HANDLER");
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        //TODO        problemDetail.setType(URI.create("https://api.exemplo.com/errors/bad-request"));
        problemDetail.setTitle(e.getMessage());
        problemDetail.setDetail(messageSource.getMessage("error.httpClientError", null, locale));
        problemDetail.setInstance(URI.create(request.getRequestURI()));
        problemDetail.setProperty("timestamp", Instant.now().toString());
        problemDetail.setProperty("method", request.getMethod());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(problemDetail);
    }
}
