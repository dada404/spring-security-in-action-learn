package com.dada.ch05formauth.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author dada
 * @date 2022/5/18-19:39
 */
@Controller
public class HelloController {

    @GetMapping("/home")
    public String home() {
        return "home.html";
    }

    @GetMapping("/error")
    public String error() {
        return "error.html";
    }
}
