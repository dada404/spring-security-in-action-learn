package com.dada.ch11business.service;

import com.dada.ch11business.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * @author dada
 * @date 2022/5/23-9:39
 */
@Service
public class UserService {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${auth.url}")
    private String url;

    @Value("${auth.auth}")
    private String auth;

    @Value("${auth.check}")
    private String check;

    public void auth(String username, String password) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);

        var entity = new HttpEntity<>(user);

        String loc = url + auth;
        var reps = restTemplate.postForEntity(loc, entity, Void.class);
    }

    public boolean check(String username, String otp) {
        User user = new User();
        user.setUsername(username);
        user.setCode(otp);

        var entity = new HttpEntity<>(user);

        String loc = url + check;
        var resp = restTemplate.postForEntity(loc, entity, Boolean.class);

        return HttpStatus.OK.equals(resp.getStatusCode());
    }


}
