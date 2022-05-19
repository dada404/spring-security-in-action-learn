package com.dada.authorityapplicationlimit.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.print.DocFlavor;

/**
 * @author dada
 * @date 2022/5/19-11:12
 */
@RestController
public class HelloController {
    @GetMapping("/hello")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public String hello() {
        return "hello";
    }
}
