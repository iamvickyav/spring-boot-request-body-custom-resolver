package com.iamvickyav.AnnotationSample.resolver;

import com.iamvickyav.AnnotationSample.StudentController;
import com.iamvickyav.AnnotationSample.security.EncryptionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdviceAdapter;

import java.lang.reflect.Type;

@ControllerAdvice
public class CustomRequestResolver extends RequestBodyAdviceAdapter {

    Logger LOGGER = LoggerFactory.getLogger(CustomRequestResolver.class);

    @Autowired
    Validator validator;

    @Autowired
    EncryptionUtil encryptionUtil;

    @Override
    public boolean supports(MethodParameter methodParameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    @Override
    public Object afterBodyRead(Object body, HttpInputMessage inputMessage, MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {

        BindingResult bindingResult = new BeanPropertyBindingResult(body, body.getClass().getCanonicalName());
        validator.validate(body, bindingResult);

        if (bindingResult.hasErrors()) {
            throw new RuntimeException("Input validation failure");
        }
        encryptionUtil.encryptSensitiveFields(body);
        return body;
    }
}
