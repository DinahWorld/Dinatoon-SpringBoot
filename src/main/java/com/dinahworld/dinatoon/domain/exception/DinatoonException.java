package com.dinahworld.dinatoon.domain.exception;

import lombok.Getter;

@Getter
public class DinatoonException extends RuntimeException {
    public DinatoonException(String error) {
        super(error);
    }
}