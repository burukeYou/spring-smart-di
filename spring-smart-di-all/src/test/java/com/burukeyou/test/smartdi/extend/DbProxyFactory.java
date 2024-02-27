package com.burukeyou.test.smartdi.extend;

import com.burukeyou.smartdi.proxyspi.factory.AnnotationProxyFactory;
import org.springframework.stereotype.Component;

@Component
public class DbProxyFactory implements AnnotationProxyFactory<DBProxySPI> {
    @Override
    public Object getProxy(Class<?> targetClass,DBProxySPI spi) {
        return null;
    }
}
