package com.dada.ch11auth.repository;

import com.dada.ch11auth.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

/**
 * @author dada
 * @date 2022/5/21-15:31
 */
public interface UserDao extends JpaRepository<User, String> {

    Optional<User> findUserByUsername(String username);
}
