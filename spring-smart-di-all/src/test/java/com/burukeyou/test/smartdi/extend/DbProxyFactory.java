package com.burukeyou.test.smartdi.extend;

import com.burukeyou.smartdi.config.SpringBeanContext;
import com.burukeyou.smartdi.proxyspi.factory.AnnotationProxyFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class DbProxyFactory implements AnnotationProxyFactory<DBProxySPI> {

    @Autowired
    private Environment environment;

    @Override
    public Object getProxy(Class<?> targetClass,DBProxySPI spi) {
        String value = environment.resolvePlaceholders(spi.value());
        Object bean = null;
        try {
            bean = SpringBeanContext.getBeanAliasName(value);
        } catch (Exception e) {
            throw new RuntimeException("can not find yml spiBean for value " + value,e);
        }
        return bean;
    }
}
