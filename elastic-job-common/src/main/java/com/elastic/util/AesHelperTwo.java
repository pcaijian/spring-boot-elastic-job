package com.elastic.util;


import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.spec.KeySpec;

/**
 * 只用于游戏机的加密工具
 * Created by TsungKang on 7/10/2017.
 */
public class AesHelperTwo {

    public static String Encrypt(String raw) throws Exception {
        Cipher c = getCipher(Cipher.ENCRYPT_MODE);
        byte[] encryptedVal = c.doFinal(raw.getBytes("UTF-8"));
        return Base64.getEncoder().encodeToString(encryptedVal);
    }


    public static String Decrypt(String encrypted) throws Exception {
        byte[] decodedValue =Base64.getDecoder().decode(encrypted);
        Cipher c = getCipher(Cipher.DECRYPT_MODE);
        byte[] decValue = c.doFinal(decodedValue);
        return new String(decValue);
    }


    public static Cipher getCipher(int mode) throws Exception {
        Cipher c = Cipher.getInstance("AES/CBC/PKCS5Padding");
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        char[] password = "AA918613343523".toCharArray();
        byte[] salt = {57,57,65,49,53,66,67,66,56,51,52,53,67,69,52,55};
        KeySpec spec = new PBEKeySpec(password, salt, 1024, 128);
        SecretKey tmp = factory.generateSecret(spec);
        byte[] encoded = tmp.getEncoded();
        byte[] iv = {70,51,70,53,52,65,50,48,56,54,65,53,51,67,69,53};
        c.init(mode, new SecretKeySpec(encoded, "AES"), new IvParameterSpec(iv));
        return c;
    }
    
}
