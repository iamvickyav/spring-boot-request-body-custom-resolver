package com.iamvickyav.AnnotationSample.config;

import com.iamvickyav.AnnotationSample.resolver.SensitiveFieldResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class AppConfiguration implements WebMvcConfigurer {

    @Autowired
    SensitiveFieldResolver sensitiveFieldResolver;

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(sensitiveFieldResolver);
    }
}