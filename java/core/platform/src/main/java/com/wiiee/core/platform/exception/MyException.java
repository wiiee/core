package com.wiiee.core.platform.exception;

public class MyException extends Exception {
    public int errorCode;

    public MyException(int errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }
}
