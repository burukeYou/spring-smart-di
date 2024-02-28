package com.burukeyou.smartdi.proxyspi.spi;


import com.burukeyou.smartdi.proxyspi.factory.EnvironmentProxyFactory;

import java.lang.annotation.*;

@Inherited
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@ProxySPI(EnvironmentProxyFactory.class)
public @interface EnvironmentProxySPI {

    /**
     * spring environment attribute
     */
    String value();

}
