package com.dada.authorityapplicationlimit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

@SpringBootApplication
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class AuthorityApplicationLimitApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuthorityApplicationLimitApplication.class, args);
    }

}
