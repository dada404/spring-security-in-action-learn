package com.dada.ch11business.security.filter;

import com.dada.ch11business.security.authentication.OtpAuthentication;
import com.dada.ch11business.security.authentication.UsernamePasswordAuthentication;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Map;

/**
 * @author dada
 * @date 2022/5/23-10:09
 */
@Component
public class InitialLoginFilter extends OncePerRequestFilter implements InitializingBean {

    @Autowired
    private AuthenticationManager manager;

    @Value("${staticKey}")
    private String staticKey;

    private Key key;

    @Override
    protected void doFilterInternal(
        HttpServletRequest request,
        HttpServletResponse response,
        FilterChain chain)
            throws ServletException, IOException {
        String code = request.getHeader("code");
        String username = request.getHeader("username");
        String password = request.getHeader("password");

        if (code == null) {
            Authentication authentication =
                new UsernamePasswordAuthentication(username, password);

            manager.authenticate(authentication);
        } else {
            Authentication authentication =
                new OtpAuthentication(username, code);

            manager.authenticate(authentication);


            String token = Jwts.builder()
                .setClaims(Map.of("username", username))
                .signWith(key)
                .compact();

            response.setHeader("Authentication", token);
        }
    }

    @Override
    public void afterPropertiesSet() throws ServletException {
        this.key = Keys.hmacShaKeyFor(staticKey.getBytes(StandardCharsets.UTF_8));
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        return !"/login".equals(request.getServletPath());
    }
}
