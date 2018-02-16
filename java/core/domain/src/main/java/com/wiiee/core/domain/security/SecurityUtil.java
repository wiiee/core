package com.wiiee.core.domain.security;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

public abstract class SecurityUtil {
    public static User getUser() {
        SecurityContext context = SecurityContextHolder.getContext();

        if (context != null) {
            Authentication authentication = context.getAuthentication();

            if (authentication != null) {
                Object principle = authentication.getPrincipal();

                if (principle != null && principle instanceof User) {
                    return (User) principle;
                }
            }
        }

        return null;
    }

    public static String getUserId() {
        Authentication authentication = getAuthentication();

        if (authentication != null) {
            return authentication.getName();
        }

        return null;
    }

    public static Authentication getAuthentication() {
        SecurityContext context = SecurityContextHolder.getContext();
        return context == null ? null : context.getAuthentication();
    }

    public static boolean isAnonymous() {
        Authentication authentication = getAuthentication();

        if (authentication != null && authentication instanceof AnonymousAuthenticationToken) {
            return true;
        }

        return false;
    }
}
