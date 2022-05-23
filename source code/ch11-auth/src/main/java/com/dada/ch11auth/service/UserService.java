package com.dada.ch11auth.service;

import com.dada.ch11auth.entity.Otp;
import com.dada.ch11auth.entity.User;
import com.dada.ch11auth.repository.OtpDao;
import com.dada.ch11auth.repository.UserDao;
import com.dada.ch11auth.util.GenerateCodeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * @author dada
 * @date 2022/5/21-15:37
 */
@Service
@Transactional
public class UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private OtpDao optDao;

    public void addUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userDao.save(user);
    }
    public void auth(User user) {
        String username = user.getUsername();

        Optional<User> optional = userDao.findUserByUsername(username);

        if (optional.isPresent()) {
            String cryptPassword = optional.get().getPassword();
            if (passwordEncoder.matches(user.getPassword(), cryptPassword)) {
                renewOpt(user);
            } else {
                throw new BadCredentialsException("USERNAME OR PASSWORD IS WRONG");
            }
        } else {
            throw new BadCredentialsException("USERNAME NOT FOUND");
        }
    }

    public boolean checkOtp(Otp otpToValidate) {
        Optional<Otp> optionalOpt = optDao.findOptByUsername(otpToValidate.getUsername());
        if (optionalOpt.isPresent()) {
            Otp otp = optionalOpt.get();

            return otp.getCode().equals(otpToValidate.getCode());
        }

        return false;
    }
    private void renewOpt(User user) {
        String code = GenerateCodeUtil.generateCode();
        Optional<Otp> userOpt = optDao.findOptByUsername(user.getUsername());

        if (userOpt.isPresent()) {
            Otp otp = userOpt.get();
            //此处为什么会更新
            otp.setCode(code);
        } else {
            Otp otp = new Otp();
            otp.setUsername(user.getUsername());
            otp.setCode(code);

            optDao.save(otp);
        }
    }
}
