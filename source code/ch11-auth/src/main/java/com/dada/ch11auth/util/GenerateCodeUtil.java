package com.dada.ch11auth.util;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * @author dada
 * @date 2022/5/21-15:46
 */
public class GenerateCodeUtil {

    private GenerateCodeUtil(){}

    public static String generateCode() {
        try {
            SecureRandom random = SecureRandom.getInstanceStrong();
            int c = random.nextInt(9000) + 1000;

            return String.valueOf(c);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(
                    "Problem when generating the random code");
        }
    }

}
