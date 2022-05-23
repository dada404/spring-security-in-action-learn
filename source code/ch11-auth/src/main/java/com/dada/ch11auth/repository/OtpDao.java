package com.dada.ch11auth.repository;

import com.dada.ch11auth.entity.Otp;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @author dada
 * @date 2022/5/21-15:33
 */
public interface OtpDao extends JpaRepository<Otp, String> {
    Optional<Otp> findOptByUsername(String username);
}
