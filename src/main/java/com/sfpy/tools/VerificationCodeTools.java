package com.sfpy.tools;


import java.util.UUID;

/**
 *生成验证码
 */
public class VerificationCodeTools {

    /**
     * 根据uuid生成
     */
    public static void createCode() {
        UUID uuid = UUID.randomUUID();

        System.out.println(uuid);
    }

    public static void main(String[] args) {
        createCode();
    }
}
