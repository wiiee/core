package com.wiiee.core.domain.security;

public interface IAccessCtrl {
    //authUserId: 登录用户Id
    //opUserId: 需要操作的用户Id
    boolean isAllowed(String authUserId, String opUserId);
}
