package com.wiiee.core.web.security;

import com.wiiee.core.domain.security.Constant;
import com.wiiee.core.platform.util.GsonUtil;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.util.CollectionUtils;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static com.wiiee.core.domain.security.Constant.*;

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

    //注册后登录成功
    public static Authentication authenticate(String username, String password, List<GrantedAuthority> authorities) {
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(username, password, authorities);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return authentication;
    }

    //返回token给客户端
    public static void setHeaderToken(HttpServletResponse response, Authentication auth) {
        String authorities = String.join(",",
                auth.getAuthorities().stream()
                        .map(o -> o.getAuthority())
                        .collect(Collectors.toList()));

        String token = Jwts.builder()
                .setSubject(auth.getName())
                .claim(Constant.AUTHORITIES_KEY, authorities)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, SECRET.getBytes())
                .compact();

        response.addHeader(HEADER_STRING, TOKEN_PREFIX + token);
        response.addHeader("Access-Control-Expose-Headers", HEADER_STRING);
    }
}
