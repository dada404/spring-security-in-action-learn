package com.dada.ch11business.security.provider;

import com.dada.ch11business.security.authentication.OtpAuthentication;
import com.dada.ch11business.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

/**
 * @author dada
 * @date 2022/5/23-10:04
 */
@Component
public class OtpAuthenticationProvider implements AuthenticationProvider {
    @Autowired
    private UserService userService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String code = (String) authentication.getCredentials();

        if (userService.check(username, code)) {
            return new UsernamePasswordAuthenticationToken(username, code);
        }

        throw new BadCredentialsException("username or code is wrong");
    }

    @Override
    public boolean supports(Class<?> clz) {
        return OtpAuthentication.class.isAssignableFrom(clz);
    }
}
