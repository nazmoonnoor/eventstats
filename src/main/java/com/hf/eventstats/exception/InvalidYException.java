package com.hf.eventstats.exception;

import lombok.Data;

@Data
public class InvalidYException extends Exception {
    private String code;

    public InvalidYException(String code, String message){
        super(message);
        this.setCode(code);
    }
}