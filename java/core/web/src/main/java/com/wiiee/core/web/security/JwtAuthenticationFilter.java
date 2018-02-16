package com.wiiee.core.web.security;

import com.wiiee.core.domain.security.Constant;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.impl.DefaultClaims;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static com.wiiee.core.domain.security.Constant.*;

public class JwtAuthenticationFilter extends BasicAuthenticationFilter {
    private static final Logger _logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    public JwtAuthenticationFilter(AuthenticationManager authManager) {
        super(authManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req,
                                    HttpServletResponse res,
                                    FilterChain chain) throws IOException, ServletException {
        String header = req.getHeader(HEADER_STRING);

        if (header == null || !header.startsWith(TOKEN_PREFIX)) {
            chain.doFilter(req, res);
            return;
        }

        UsernamePasswordAuthenticationToken authentication = getAuthentication(req);

        SecurityContextHolder.getContext().setAuthentication(authentication);

        chain.doFilter(req, res);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        String token = request.getHeader(HEADER_STRING);
        if (token != null) {
            try {
                // parse the token.
                DefaultClaims body = (DefaultClaims) Jwts.parser()
                        .setSigningKey(SECRET.getBytes())
                        .parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
                        .getBody();

                String user = body.getSubject();
                List<GrantedAuthority> authorities = (List<GrantedAuthority>) body.get(Constant.AUTHORITIES_KEY);

                if (user != null) {
                    return new UsernamePasswordAuthenticationToken(user, null, authorities);
                }
            } catch (Exception ex) {
                _logger.error(ex.getMessage());
            }

            return null;
        }

        return null;
    }
}
