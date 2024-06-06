package com.simpletiktok.simpletiktok.exception;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler
{
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorResponse handleConstraintViolationException(ConstraintViolationException ex)
    {
        List<String> errors = new ArrayList<>();
        ex.getConstraintViolations().forEach(error -> errors.add(error.getMessage()));
        return new ErrorResponse("Validation failed", errors);
    }

    public static class ErrorResponse
    {
        private String message;
        private List<String> errors;

        public ErrorResponse(String message, List<String> errors)
        {
            this.message = message;
            this.errors = errors;
        }
    }
}
