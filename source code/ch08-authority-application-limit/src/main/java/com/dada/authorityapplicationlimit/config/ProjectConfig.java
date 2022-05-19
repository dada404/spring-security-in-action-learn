package com.dada.authorityapplicationlimit.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

/**
 * @author dada
 * @date 2022/5/19-10:46
 */
@Configuration
public class ProjectConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic();

        http.authorizeRequests()
                .mvcMatchers("/public").permitAll()
                .mvcMatchers(HttpMethod.DELETE, "/hello").hasAnyRole("USER")
                .mvcMatchers("/auth").authenticated()
                .mvcMatchers("/**").denyAll();
    }

    @Override
    @Bean
    public UserDetailsService userDetailsService() {
        var manager = new InMemoryUserDetailsManager();

        var user1 = User.withUsername("a1")
                .password("123")
                .authorities("READ", "WRITE")
                .roles("MANAGER") //.authorities("ROLE_MANAGER")
                .build();
        var user2 = User.withUsername("a2")
                .password("123")
                .authorities("READ")
                .roles("USER")
                .build();

        manager.createUser(user1);
        manager.createUser(user2);

        return manager;
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }
}
