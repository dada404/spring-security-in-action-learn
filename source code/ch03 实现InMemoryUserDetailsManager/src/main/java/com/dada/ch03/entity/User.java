package com.dada.ch03.entity;

import lombok.Data;

/**
 * @author dada
 * @date 2022/5/16-19:46
 */
@Data
public class User {
    private String username;
    
    private String password;
    
    private String authority;
}
