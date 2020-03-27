package com.iamvickyav.AnnotationSample.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ApplicationContextException;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.PriorityOrdered;
import org.springframework.core.env.Environment;

@Configuration
public class SecurityVerifier implements BeanFactoryPostProcessor, PriorityOrdered {

    Logger LOGGER = LoggerFactory.getLogger(SecurityVerifier.class);

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {

        Environment environment = configurableListableBeanFactory.getBean(Environment.class);

        if(environment.getProperty("enc.key2") == null) {
           // throw new ApplicationContextException("Mandatory property missing on context " + " enc.key2");
            LOGGER.warn("Missing property enc.key2 in context; will use default enc key");
        }
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }
}
