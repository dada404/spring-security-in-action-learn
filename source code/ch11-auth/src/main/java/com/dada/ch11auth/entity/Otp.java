package com.dada.ch11auth.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * @author dada
 * @date 2022/5/21-15:26
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Otp {

    @Id
    private String username;

    private String code;
}
