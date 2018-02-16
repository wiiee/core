package com.wiiee.core.web.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wiiee.core.domain.security.AuthUser;
import com.wiiee.core.domain.security.Constant;
import com.wiiee.core.platform.util.GsonUtil;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import static com.wiiee.core.domain.security.Constant.*;

//登录认证
public class JwtUsernamePasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private static final Logger _logger = LoggerFactory.getLogger(JwtUsernamePasswordAuthenticationFilter.class);

    private AuthenticationManager authenticationManager;

    public JwtUsernamePasswordAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest req,
                                                HttpServletResponse res) throws AuthenticationException {
        try {
            AuthUser credentials = new ObjectMapper()
                    .readValue(req.getInputStream(), AuthUser.class);

            return authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            credentials.username,
                            credentials.password,
                            new ArrayList<>())
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest req,
                                            HttpServletResponse res,
                                            FilterChain chain,
                                            Authentication auth) throws IOException, ServletException {
        if(auth.getPrincipal() instanceof User){
            User user = (User)auth.getPrincipal();
            String token = Jwts.builder()
                    .setSubject(user.getUsername())
                    .claim(Constant.AUTHORITIES_KEY, user.getAuthorities())
                    .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                    .signWith(SignatureAlgorithm.HS512, SECRET.getBytes())
                    .compact();

            res.addHeader(HEADER_STRING, TOKEN_PREFIX + token);
            res.addHeader("Access-Control-Expose-Headers", HEADER_STRING);
        }
        else{
            _logger.warn(GsonUtil.toJson(auth));
        }
    }
}