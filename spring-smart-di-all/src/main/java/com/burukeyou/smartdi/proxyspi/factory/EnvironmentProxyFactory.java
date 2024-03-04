package com.burukeyou.smartdi.proxyspi.factory;

import com.burukeyou.smartdi.config.SpringBeanContext;
import com.burukeyou.smartdi.proxyspi.spi.EnvironmentProxySPI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

/**
 * @author  caizhihao
 */
@Component
public class EnvironmentProxyFactory implements AnnotationProxyFactory<EnvironmentProxySPI> {

    @Autowired
    private Environment environment;

    @Override
    public Object getProxy(Class<?> targetClass, EnvironmentProxySPI spi) {
        String value = environment.resolvePlaceholders(spi.value());
        Object bean = null;
        try {
            bean = SpringBeanContext.getBeanByAllName(value);
        } catch (Exception e) {
            throw new RuntimeException("can not find yml spiBean for value " + value,e);
        }
        return bean;
    }

}
