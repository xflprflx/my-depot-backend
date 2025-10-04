package com.xflprflx.my_depot_backend.exceptions;


import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;

import java.net.URI;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
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

    @ExceptionHandler(MethodArgumentNotValidException.class)
    private ResponseEntity<ProblemDetail> handleMethodArgumentNotValidException(MethodArgumentNotValidException e, HttpServletRequest request, Locale locale) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        //TODO        problemDetail.setType(URI.create("https://api.exemplo.com/errors/method-argument-not-valid-exception"));
        problemDetail.setTitle(e.getMessage());
        problemDetail.setDetail("Dados inválidos na requisição.");
        problemDetail.setInstance(URI.create(request.getRequestURI()));
        problemDetail.setProperty("timestamp", Instant.now().toString());
        problemDetail.setProperty("method", request.getMethod());
        List<FieldMessage> errors = new ArrayList<>();
        for (FieldError fieldError : e.getBindingResult().getFieldErrors()) {
            String errorMessage = getErrorMessage(fieldError);
            errors.add(new FieldMessage(fieldError.getField(), errorMessage));
        }
        problemDetail.setProperty("errors", errors);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(problemDetail);
    }

    private String getErrorMessage(ObjectError error) {
        return messageSource.getMessage(error, LocaleContextHolder.getLocale());
    }
}
