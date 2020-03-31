package com.iamvickyav.AnnotationSample.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;

@Component(value = "AES")
public class AESAlgorithmEncryptor implements Encryptor {

    private static SecretKeySpec secretKey;
    private static byte[] key;

    @Value("${enc.key1}")
    private String keyFromProperty;

    @Autowired
    Environment environment;

    public String encrypt(String strToEncrypt) {
        try {
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            return Base64.getEncoder().encodeToString(cipher.doFinal(strToEncrypt.getBytes("UTF-8")));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String decrypt(String strToDecrypt) {
        try {
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            return new String(cipher.doFinal(Base64.getDecoder().decode(strToDecrypt)));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @PostConstruct
    private void prepareKey() throws UnsupportedEncodingException, NoSuchAlgorithmException {
        String secret = generateSecret();
        MessageDigest sha = null;
        key = secret.getBytes("UTF-8");
        sha = MessageDigest.getInstance("SHA-1");
        key = sha.digest(key);
        key = Arrays.copyOf(key, 16);
        secretKey = new SecretKeySpec(key, "AES");
    }

    private String generateSecret() {
        String keyFromEnvironment = environment.getProperty("enc.key2");
        if(keyFromEnvironment == null)
            keyFromEnvironment = "csk";
        return keyFromProperty + keyFromEnvironment;
    }
}