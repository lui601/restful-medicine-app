package com.example.util;

import com.google.rpc.Status;
import lombok.Getter;

@Getter
public class CustomException extends Exception {

    private final Status status;

    public CustomException(Status status) {
        super(status.getMessage());
        this.status = status;
    }
}