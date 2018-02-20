package com.wiiee.core.web.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wiiee.core.domain.security.AuthUser;
import com.wiiee.core.domain.security.Constant;
import com.wiiee.core.platform.property.AppProperties;
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
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import static com.wiiee.core.domain.security.Constant.*;

//登录认证
@Component
public class JwtUsernamePasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private static final Logger _logger = LoggerFactory.getLogger(JwtUsernamePasswordAuthenticationFilter.class);

    public JwtUsernamePasswordAuthenticationFilter(AuthenticationManager authenticationManager, AppProperties appProperties) {
        setAuthenticationManager(authenticationManager);

        if (!StringUtils.isEmpty(appProperties.getAuthenticationUrl())) {
            this.setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher(appProperties.getAuthenticationUrl(), "POST"));
        }
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest req,
                                                HttpServletResponse res) throws AuthenticationException {
        try {
            AuthUser credentials = new ObjectMapper()
                    .readValue(req.getInputStream(), AuthUser.class);

            return getAuthenticationManager().authenticate(
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
        SecurityUtil.setHeaderToken(res, auth);
    }
}