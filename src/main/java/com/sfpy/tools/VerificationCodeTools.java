package com.sfpy.tools;


import java.util.UUID;

/**
 *������֤��
 */
public class VerificationCodeTools {

    /**
     * ����uuid����
     */
    public static void createCode() {
        UUID uuid = UUID.randomUUID();

        System.out.println(uuid);
    }

    public static void main(String[] args) {
        createCode();
    }
}
