package com.hf.eventstats.exception;

import lombok.Data;

@Data
public class InvalidXException extends Exception {
    private String code;

    public InvalidXException(String code, String message){
        super(message);
        this.setCode(code);
    }
}