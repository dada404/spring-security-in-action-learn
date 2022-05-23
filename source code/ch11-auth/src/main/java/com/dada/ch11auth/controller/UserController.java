package com.dada.ch11auth.controller;

import com.dada.ch11auth.entity.Otp;
import com.dada.ch11auth.entity.User;
import com.dada.ch11auth.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

/**
 * @author dada
 * @date 2022/5/21-16:01
 */
@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("user")
    public void addUser(User user) {
        userService.addUser(user);
    }

    @PostMapping("user/auth")
    public void auth(@RequestBody User user) {
        userService.auth(user);
    }

    @PostMapping("check")
    public void check(@RequestBody Otp otp, HttpServletResponse response) {
        if (userService.checkOtp(otp)) {
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        }
    }
}
