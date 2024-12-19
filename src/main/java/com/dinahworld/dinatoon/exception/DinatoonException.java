package com.dinahworld.dinatoon.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class DinatoonException extends RuntimeException {
    private HttpStatus status;

    public DinatoonException(String error) {
        super(error);
    }

    public DinatoonException(String error, HttpStatus status) {
        super(error);
        this.status = status;
    }
}