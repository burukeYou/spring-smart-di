package com.burukeyou.smartdi.proxyspi.factory;

import com.burukeyou.smartdi.config.SpringBeanContext;
import com.burukeyou.smartdi.proxyspi.annotation.EnvironmentProxySPI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class EnvironmentProxyFactory implements AnnotationProxyFactory<EnvironmentProxySPI> {

    @Autowired
    private Environment environment;

    @Override
    public Object getProxy(Class<?> targetClass, EnvironmentProxySPI spi) {
        String value = environment.resolvePlaceholders(spi.value());
        // 这时候注入SmsService，但是要获取的那个bean还没注册到spring容器中可能还没注入，所以不能用BeanPostProcessor，bean的注入顺序是不确定
        Object bean = null;
        try {
            bean = SpringBeanContext.getBeanByAliasName(value);
        } catch (Exception e) {
            throw new RuntimeException("can not find yml spiBean for value " + value,e);
        }
        return bean;
    }

}
