package com.dada.ch11business.security.provider;

import com.dada.ch11business.security.authentication.UsernamePasswordAuthentication;
import com.dada.ch11business.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import java.util.UnknownFormatConversionException;

/**
 * @author dada
 * @date 2022/5/23-9:37
 */
@Component
public class UsernamePasswordProvider implements AuthenticationProvider {

    @Autowired
    private UserService userService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = (String) authentication.getCredentials();

        userService.auth(username, password);

        return new UsernamePasswordAuthenticationToken(username, password);
    }

    @Override
    public boolean supports(Class<?> clz) {
        return UsernamePasswordAuthentication.class.isAssignableFrom(clz);
    }
}
