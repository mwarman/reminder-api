package com.example.reminderapi.controller;

import org.springframework.http.HttpStatus;

public class ControllerError {

    private int status = HttpStatus.INTERNAL_SERVER_ERROR.value();

    private String error = HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase();

    private String message;

    public ControllerError(HttpStatus httpStatus) {
        this.status = httpStatus.value();
        this.error = httpStatus.getReasonPhrase();
    }

    public ControllerError(HttpStatus httpStatus, String message) {
        this(httpStatus);
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
