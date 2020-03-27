package com.iamvickyav.AnnotationSample.security;

public interface Encryptor {
    String encrypt(String originalInput);
    String decrypt(String encryptedInput);
}