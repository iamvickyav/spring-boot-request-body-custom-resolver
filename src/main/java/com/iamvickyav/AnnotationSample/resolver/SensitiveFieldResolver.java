package com.iamvickyav.AnnotationSample.resolver;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iamvickyav.AnnotationSample.dto.Student;
import com.iamvickyav.AnnotationSample.security.EncryptionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;

@Component
public class SensitiveFieldResolver implements HandlerMethodArgumentResolver {

    @Autowired
    EncryptionUtil encryptionUtil;

    @Autowired
    ObjectMapper objectMapper;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().equals(Student.class);
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest webRequest, WebDataBinderFactory webDataBinderFactory) throws Exception {

        Object resolvedObject = convertToObject((HttpServletRequest) webRequest.getNativeRequest(), methodParameter);

        if(methodParameter.hasParameterAnnotation(Valid.class)) {
            WebDataBinder dataBinder = webDataBinderFactory.createBinder(webRequest, resolvedObject,"resolvedObject");
            dataBinder.validate();
            BindingResult bindingResult = dataBinder.getBindingResult();
            if(bindingResult.hasErrors()) {
                throw new RuntimeException("Issue with data biding");
            }
        }
        encryptionUtil.encryptSensitiveFields(resolvedObject);
        return resolvedObject;
    }

    private Object convertToObject(HttpServletRequest request, MethodParameter methodParameter) {
        Object resolvedObject = null;
        try {
            resolvedObject = objectMapper.readValue(request.getInputStream(), methodParameter.getParameterType());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return resolvedObject;
    }
}