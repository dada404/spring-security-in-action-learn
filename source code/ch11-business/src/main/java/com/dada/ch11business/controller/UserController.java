package com.dada.ch11business.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author dada
 * @date 2022/5/23-11:05
 */
@RestController
public class UserController {

    @GetMapping("/test")
    public String test() {
        return "test";
    }
}
