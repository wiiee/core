package com.wiiee.core.platform.exception;

public abstract class CoreException {
    //1 ~ 1000 System Error
    public static final MyException EXCEPTION_SYSTEM = new MyException(1, "");

    //1001 ~ 2000 Service Error
    public static final MyException EXCEPTION_SERVICE = new MyException(1001, "");

    //2001 ~ 5000 Domain Error
    public static final MyException EXCEPTION_DOMAIN = new MyException(2001, "");
}