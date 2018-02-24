package com.wiiee.core.web.security;

import com.wiiee.core.domain.security.SecurityConstant;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.stream.Collectors;

import static com.wiiee.core.domain.security.SecurityConstant.*;

public abstract class WebSecurityUtil {
    //返回token给客户端
    public static void setHeaderToken(HttpServletResponse response, Authentication auth) {
        String authorities = String.join(",",
                auth.getAuthorities().stream()
                        .map(o -> o.getAuthority())
                        .collect(Collectors.toList()));

        String token = Jwts.builder()
                .setSubject(auth.getName())
                .claim(SecurityConstant.AUTHORITIES_KEY, authorities)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, SECRET.getBytes())
                .compact();

        response.addHeader(HEADER_STRING, TOKEN_PREFIX + token);
        response.addHeader("Access-Control-Expose-Headers", HEADER_STRING);
    }
}
