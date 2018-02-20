package com.wiiee.core.domain.security;

public class AuthUser {
    public String username;
    public String password;

    public AuthUser(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
