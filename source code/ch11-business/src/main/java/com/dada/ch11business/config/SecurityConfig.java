package com.dada.ch11business.config;

import com.dada.ch11business.security.filter.InitialLoginFilter;
import com.dada.ch11business.security.filter.JwtFilter;
import com.dada.ch11business.security.provider.OtpAuthenticationProvider;
import com.dada.ch11business.security.provider.UsernamePasswordProvider;
import com.sun.jdi.PrimitiveValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.stereotype.Component;

/**
 * @author dada
 * @date 2022/5/21-17:18
 */
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private JwtFilter jwtFilter;

    @Autowired
    private InitialLoginFilter loginFilter;

    @Autowired
    private OtpAuthenticationProvider otpAuthenticationProvider;

    @Autowired
    private UsernamePasswordProvider usernamePasswordProvider;

    @Bean
    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(otpAuthenticationProvider)
            .authenticationProvider(usernamePasswordProvider);
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();

        http.addFilterAt(loginFilter, BasicAuthenticationFilter.class)
                .addFilterAfter(jwtFilter, BasicAuthenticationFilter.class);

        http.authorizeRequests().anyRequest().authenticated();
    }
}
