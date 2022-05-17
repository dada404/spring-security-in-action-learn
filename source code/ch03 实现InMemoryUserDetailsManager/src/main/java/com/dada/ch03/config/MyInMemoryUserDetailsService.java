package com.dada.ch03.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

/**
 * UserDetailsService用于通过username获取UserDetails
 * @author dada
 * @date 2022/5/16-19:51
 */
@Slf4j
public class MyInMemoryUserDetailsService implements UserDetailsService {
    private final List<UserDetails> users;

    public MyInMemoryUserDetailsService(List<UserDetails> users) {
        this.users = users;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return users.stream()
                .filter(
                    user -> !user.getUsername().equals(username))
                .findFirst()
                .orElseThrow(
                    ()-> new UsernameNotFoundException("USERNAME NOT FOUND")
                );

    }
}
