package com.ismailjacoby.bankingapi.exceptions;

import com.ismailjacoby.bankingapi.dto.exception.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {
    // AccessDenied Exception
    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErrorResponse handleAccessDeniedException(AccessDeniedException e) {
        return new ErrorResponse("Forbidden", e.getMessage(), HttpStatus.FORBIDDEN);
    }

    // BadCredentialsException (incorrect username or password)
    @ExceptionHandler(BadCredentialsException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorResponse handleBadCredentialsException(BadCredentialsException e) {
        return new ErrorResponse("Authentication failed", e.getMessage(), HttpStatus.UNAUTHORIZED);
    }

    // DuplicateException
    @ExceptionHandler(DuplicateException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleDuplicateException(DuplicateException e) {
        return new ErrorResponse("Duplicate Found", e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    // IllegalArgumentException
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleIllegalArgumentException(IllegalArgumentException e) {
        return new ErrorResponse("Invalid Argument", e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    // MethodArgumentNotValidException (Validations)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        List<String> errorMessages = e.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(fieldError -> fieldError.getDefaultMessage())
                .collect(Collectors.toList());

        String message = String.join(", ", errorMessages);

        return new ErrorResponse("Validation failed", message, HttpStatus.BAD_REQUEST);
    }

    // NotFoundException
    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleNotFoundException(NotFoundException e) {
        return new ErrorResponse("Not Found", e.getMessage(), HttpStatus.NOT_FOUND);
    }
}
