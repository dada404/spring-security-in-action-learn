package com.dada.ch11business.security.filter;

import com.dada.ch11business.security.authentication.UsernamePasswordAuthentication;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.ref.ReferenceQueue;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.Security;
import java.util.List;

/**
 * @author dada
 * @date 2022/5/23-10:25
 */
@Component
public class JwtFilter
    extends OncePerRequestFilter implements InitializingBean {
    @Value("${staticKey}")
    private String staticKey;

    private Key key;

    @Override
    protected void doFilterInternal(
        HttpServletRequest request,
        HttpServletResponse response,
        FilterChain filterChain)
            throws ServletException, IOException {

        String token = request.getHeader("authentication");
        if (token == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        Claims body = Jwts.parserBuilder()
            .setSigningKey(key)
            .build()
            .parseClaimsJws(token)
            .getBody();

        String username = body.get("username", String.class);

        var simpleAuthority = new SimpleGrantedAuthority("user");
        var auth = new UsernamePasswordAuthentication(
                username,
                null
                ,List.of(simpleAuthority));

        SecurityContextHolder.getContext().setAuthentication(auth);

        filterChain.doFilter(request, response);
    }

    @Override
    public void afterPropertiesSet() throws ServletException {
        this.key = Keys.hmacShaKeyFor(staticKey.getBytes(StandardCharsets.UTF_8));
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return "/login".equals(request.getServletPath());
    }
}
