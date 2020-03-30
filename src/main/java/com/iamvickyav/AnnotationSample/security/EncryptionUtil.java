package com.iamvickyav.AnnotationSample.security;

import com.iamvickyav.AnnotationSample.annotation.SensitiveField;
import com.iamvickyav.AnnotationSample.config.SecurityVerifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;

@Component
public class EncryptionUtil {

    Logger LOGGER = LoggerFactory.getLogger(EncryptionUtil.class);

    @Autowired
    Encryptor encryptor;

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
                    String processedValue ;
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
