package com.dada.ch0302.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;

import javax.sql.DataSource;

/**
 * @author dada
 * @date 2022/5/17-9:24
 */
@Configuration
public class ProjectConfig {

//    @Bean
//    public UserDetailsManager userDetailsManager(DataSource dataSource) {
//        return new JdbcUserDetailsManager(dataSource);
//    }
    @Bean
    public UserDetailsManager userDetailsManager(DataSource dataSource) {
        String queryUserByUsername = "SELECT number, password, enabled FROM users WHERE number = ?";
        String queryAuthsByUsername = "SELECT number, authority FROM authorities WHERE number = ?";

        var userDetailsManager = new JdbcUserDetailsManager(dataSource);
        userDetailsManager.setUsersByUsernameQuery(queryUserByUsername);
        userDetailsManager.setAuthoritiesByUsernameQuery(queryAuthsByUsername);

        return userDetailsManager;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }
}
