package com.wiiee.core.domain.security;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

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

    public static List<String> getAuthorities() {
        Authentication authentication = getAuthentication();

        if (authentication != null) {
            Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
            if (!CollectionUtils.isEmpty(authorities)) {
                return authorities.stream().map(o -> o.getAuthority()).collect(Collectors.toList());
            }
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

    //注册后登录成功
    public static Authentication authenticate(String username, String password, List<GrantedAuthority> authorities) {
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(username, password, authorities);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return authentication;
    }
}
