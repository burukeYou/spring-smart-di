package com.burukeyou.smartdi.proxyspi;

import com.burukeyou.smartdi.exceptions.AutowiredInjectBeanException;
import com.burukeyou.smartdi.proxyspi.spi.AutowiredSPI;
import com.burukeyou.smartdi.register.BaseAutowiredBeanProcessor;
import com.burukeyou.smartdi.support.AutowiredInvocation;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.util.Collections;
import java.util.List;


/**
 * @author  caizhihao
 */
@Component
public class SPIAutowiredBeanProcessor extends BaseAutowiredBeanProcessor {

    @Override
    public List<Class<? extends Annotation>> interceptAnnotation() {
        return Collections.singletonList(AutowiredSPI.class);
    }

    @Override
    public   Object getInjectedBean(AutowiredInvocation invocation) {
        AutowiredSPI annotationObj = invocation.getAnnotationMeta().getAnnotationObj();
        Class<?> injectedType = invocation.getInjectedType();
        Object bean = ProxySpiLoader.load(injectedType);
        if (bean == null && annotationObj.required()){
            throw new AutowiredInjectBeanException("can not find SPI Bean for " + invocation.getInjectedType());
        }
        return bean;
    }
}
