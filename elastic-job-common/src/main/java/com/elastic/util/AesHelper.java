package com.elastic.util;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import com.sun.crypto.provider.SunJCE;
import java.security.*;
import java.security.spec.KeySpec;
import java.util.Base64;

/**
 * aes非对称加密(在非对称加密基础上再加密)
 * Created by TsungKang on 5/23/2017.
 */
public class AesHelper {
    public static String Encrypt(String raw) throws Exception {
        Cipher c = getCipher(Cipher.ENCRYPT_MODE);
        byte[] encryptedVal = c.doFinal(raw.getBytes("UTF-8"));
        return Base64.getEncoder().encodeToString(encryptedVal);
    }

    public static Cipher getCipher(int mode) throws Exception {
        Cipher c = Cipher.getInstance("AES/CBC/PKCS5Padding", new SunJCE());
        byte[] iv = {119,111,99,97,111,110,105,100,97,121,101,33,64,35,36,37};
        c.init(mode, generateKey(), new IvParameterSpec(iv));
        return c;
    }

    public static String Decrypt(String encrypted) throws Exception {
        byte[] decodedValue = Base64.getDecoder().decode(encrypted);
        Cipher c = getCipher(Cipher.DECRYPT_MODE);
        byte[] decValue = c.doFinal(decodedValue);
        return new String(decValue);
    }

    public static Key generateKey() throws Exception {
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        char[] password = "9c4f78adb2fa9d7".toCharArray();
        byte[] salt = {99,53,102,53,56,49,50,97,52,55,50,50,52,101,56,48,52};

        KeySpec spec = new PBEKeySpec(password, salt, 65536, 128);
        SecretKey tmp = factory.generateSecret(spec);
        byte[] encoded = tmp.getEncoded();
        return new SecretKeySpec(encoded, "AES");
    }
    
    
}
