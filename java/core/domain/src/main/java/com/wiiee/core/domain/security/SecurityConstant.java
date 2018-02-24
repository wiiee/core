package com.wiiee.core.domain.security;

public abstract class SecurityConstant {
    public static final String SECRET = "SecretKeyToGenJWTs";
    public static final long EXPIRATION_TIME = 864_000_000; // 10 days
    public static final String TOKEN_PREFIX = "Security ";
    public static final String HEADER_STRING = "Authorization";
    public static final String SIGN_UP_URL = "/api/user/signUp";
    public static final String AUTHORITIES_KEY = "authorities";
}
