package com.iamvickyav.AnnotationSample.annotation;

import com.iamvickyav.AnnotationSample.security.SecureAlgorithm;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface SensitiveField {
    public SecureAlgorithm algorithm() default SecureAlgorithm.AES;
}