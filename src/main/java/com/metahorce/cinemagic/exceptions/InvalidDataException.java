package com.metahorce.cinemagic.exceptions;

import org.springframework.validation.BindingResult;

public class InvalidDataException extends RuntimeException {

    private BindingResult bindingResult;

    public InvalidDataException(String message, BindingResult bindingResult) {
        super(message);
        this.bindingResult = bindingResult;
    }

    public BindingResult getBindingResult() {
        return bindingResult;
    }

    public void setBindingResult(BindingResult bindingResult) {
        this.bindingResult = bindingResult;
    }

}
