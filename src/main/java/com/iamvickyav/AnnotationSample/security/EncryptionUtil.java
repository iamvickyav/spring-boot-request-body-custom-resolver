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
        Field[] fieldList = inputObj.getClass().getDeclaredFields();
        for(Field field: fieldList) {
            if(field.isAnnotationPresent(SensitiveField.class)) {
                field.setAccessible(true);
                try {
                    String originalValue = (String) field.get(inputObj);
                    String encryptedValue = encryptor.encrypt(originalValue);
                    field.set(inputObj, encryptedValue);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        LOGGER.info("Sensitive Fields encrypted successfully");
    }
}
