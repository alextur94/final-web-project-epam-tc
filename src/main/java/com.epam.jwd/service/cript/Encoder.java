package com.epam.jwd.service.cript;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class Encoder {
    public static synchronized String hashPass(String password) throws IllegalBlockSizeException, InvalidKeyException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException {
        StringBuffer hash = new StringBuffer();
        SecretKeySpec key = Encoder.key();
        byte[] bytes = Encoder.encrypt(password, key);
        for (byte aByte : bytes) { hash.append(aByte); }
        return String.valueOf(hash);
    }

    public static SecretKeySpec key(){
        SecretKeySpec key = new SecretKeySpec("My2U5niQue8code5".getBytes(StandardCharsets.UTF_8), "AES");
        return key;
    }

    public static byte[] encrypt(String password, SecretKeySpec key) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        String string = password;
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] bytes = cipher.doFinal(string.getBytes(StandardCharsets.UTF_8));
        return bytes;
    }

    public static byte[] decrypt(byte[] criptPass, SecretKeySpec key) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        Cipher decriptCipher = Cipher.getInstance("AES");
        decriptCipher.init(Cipher.DECRYPT_MODE, key);
        byte[] bytes = decriptCipher.doFinal(criptPass);
        return bytes;
    }
}
