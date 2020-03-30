package com.iamvickyav.AnnotationSample.resolver;

import com.iamvickyav.AnnotationSample.dto.Student;
import com.iamvickyav.AnnotationSample.security.EncryptionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

@ControllerAdvice
public class CustomResponseResolver implements ResponseBodyAdvice<Student> {

    Logger LOGGER = LoggerFactory.getLogger(CustomResponseResolver.class);

    @Autowired
    EncryptionUtil encryptionUtil;

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return returnType.getParameterType() == Student.class;
    }

    @Override
    public Student beforeBodyWrite(Student body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        LOGGER.debug("CustomResponseResolver called Student return type");
        encryptionUtil.decryptSensitiveFields(body);
        return body;
    }
}
