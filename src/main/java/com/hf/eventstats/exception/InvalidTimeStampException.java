package com.hf.eventstats.exception;

import lombok.Data;

@Data
public class InvalidTimeStampException extends Exception {
    private String code;

    public InvalidTimeStampException(String code, String message){
        super(message);
        this.setCode(code);
    }
}
