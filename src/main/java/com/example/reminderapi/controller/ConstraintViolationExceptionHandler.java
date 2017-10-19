package com.example.reminderapi.controller;

import java.util.Iterator;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@Order(100)
public class ConstraintViolationExceptionHandler
        extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseBody
    ResponseEntity<?> handleConstraintViolationException(
            ConstraintViolationException ex) {

        return new ResponseEntity<>(
                new ControllerError(HttpStatus.BAD_REQUEST,
                        getMessage(ex)),
                HttpStatus.BAD_REQUEST);

    }

    private String getMessage(ConstraintViolationException ex) {
        StringBuilder builder = new StringBuilder("Invalid data detected: ");
        Iterator<ConstraintViolation<?>> violations = ex
                .getConstraintViolations().iterator();

        while (violations.hasNext()) {
            ConstraintViolation<?> violation = violations.next();
            builder.append(violation.getRootBean().getClass().getSimpleName());
            builder.append(".");
            builder.append(violation.getPropertyPath());
            builder.append(" ");
            builder.append(violation.getMessage());
            if (violations.hasNext()) {
                builder.append("; ");
            }

        }
        builder.append(".");

        return builder.toString();
    }

}
