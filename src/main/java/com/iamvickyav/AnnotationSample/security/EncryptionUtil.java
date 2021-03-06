package com.iamvickyav.AnnotationSample.security;

import com.iamvickyav.AnnotationSample.annotation.SensitiveField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.Map;

@Component
public class EncryptionUtil {

    Logger LOGGER = LoggerFactory.getLogger(EncryptionUtil.class);

    @Autowired
    Map<String,Encryptor> encryptorMap;

    public void encryptSensitiveFields(Object inputObj) {
        LOGGER.info("Request received for Encrypting Sensitive Fields in Object");
        processFields(inputObj, SecurityProcess.ENCRYPT);
        LOGGER.info("Sensitive Fields encrypted successfully");
    }

    public void decryptSensitiveFields(Object inputObj) {
        LOGGER.info("Request received for Decrypting Sensitive Fields in Object");
        processFields(inputObj, SecurityProcess.DECRYPT);
        LOGGER.info("Sensitive Fields decrypted successfully");
    }

    void processFields(Object input, SecurityProcess securityProcess) {
        Field[] fieldList = input.getClass().getDeclaredFields();
        for(Field field: fieldList) {
            if(field.isAnnotationPresent(SensitiveField.class)) {
                field.setAccessible(true);
                try {
                    String originalValue = (String) field.get(input);
                    String processedValue;
                    SecureAlgorithm algorithm = field.getAnnotation(SensitiveField.class).algorithm();
                    Encryptor encryptor = encryptorMap.get(algorithm.name());
                    if(securityProcess.equals(SecurityProcess.ENCRYPT))
                        processedValue = encryptor.encrypt(originalValue);
                    else
                        processedValue = encryptor.decrypt(originalValue);
                    field.set(input, processedValue);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
