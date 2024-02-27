package com.burukeyou.smartdi.proxyspi.annotation;


import com.burukeyou.smartdi.proxyspi.factory.EnvironmentProxyFactory;

import java.lang.annotation.*;

@Inherited
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@ProxySPI(EnvironmentProxyFactory.class)
public @interface EnvironmentProxySPI {

    /**
     * spring environment 属性
     */
    String value();

}
