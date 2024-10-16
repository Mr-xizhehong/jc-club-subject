package com.jingdianjichi.subject.infra.basic.utils;

import com.alibaba.druid.filter.config.ConfigTools;

import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

public class DruidUtil {
    private static String publicKey;
    private static String privateKey;

    static {
        try {
            String[] strings = ConfigTools.genKeyPair(512);
            publicKey = strings[1];
            privateKey = strings[0];
            System.out.println("publicKey:" + publicKey);
            System.out.println("privateKey:" + privateKey);
        } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
        } catch (NoSuchProviderException e) {
                e.printStackTrace();
        }
    }

    public static String encrypt(String password) throws Exception {
        return ConfigTools.encrypt(privateKey, password);
    }

    public static String decrypt(String password) throws Exception {
        return ConfigTools.decrypt(publicKey, password);
    }

    public static void main(String[] args) throws Exception {
        String encrypt = encrypt("123456");
        System.out.println("encrypt:" + encrypt);

    }
}
