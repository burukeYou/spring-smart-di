package com.burukeyou.smartdi.proxyspi.annotation;


import com.burukeyou.smartdi.proxyspi.factory.AnnotationProxyFactory;

import java.lang.annotation.*;

@Inherited
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ProxySPI {

    /**
     * 获取代理的Bean工厂
     */
    Class<? extends AnnotationProxyFactory<?>> value();


}
