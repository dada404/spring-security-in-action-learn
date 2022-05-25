package com.dada.ch12oauth2sso.controller;

import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.logging.Logger;

/**
 * @author dada
 * @date 2022/5/24-9:21
 */
@Controller
public class MainController {
    private Logger log = Logger.getLogger(MainController.class.getName());

    @GetMapping("/")
    public String main(OAuth2AuthenticationToken token) {
        log.info(String.valueOf(token.getPrincipal()));
        return "main.html";
    }
}
