package com.wiiee.core.platform.exception;

import org.springframework.stereotype.Component;

@Component
public class CoreException implements BaseException {
    //1 ~ 1000 System Error
    public static final MyException EXCEPTION_SYSTEM = new MyException(1, "System error.");
    public static final MyException EXCEPTION_ACCESS_DENIED = new MyException(403, "You don't have right to access the resource, please check it.");

    //1001 ~ 5000 Service Error
    public static final MyException EXCEPTION_SERVICE = new MyException(1001, "Service error.");
    public static final MyException EXCEPTION_INVALID_DATA = new MyException(1002, "Invalid data, please check it.");
    public static final MyException EXCEPTION_NULL_PARAMETERS = new MyException(1003, "Null parameters, please check it.");

    public static final MyException INVALID_USERNAME = new MyException(1021, "Id is invalid, please check it.");
    public static final MyException INVALID_PWD = new MyException(1022, "Password is invalid, please check it.");
    public static final MyException INVALID_USERNAME_OR_PWD = new MyException(1023, "Id or password is invalid, please check it.");
    public static final MyException USER_ALREADY_EXIST = new MyException(1024, "User is already exist.");
}