package com.burukeyou.test.smartdi.extend;


import com.burukeyou.smartdi.proxyspi.annotation.ProxySPI;
import com.burukeyou.smartdi.proxyspi.factory.EnvironmentProxyFactory;

import java.lang.annotation.*;

@Inherited
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@ProxySPI(EnvironmentProxyFactory.class)
public @interface DBProxySPI {

    /**
     * 属性
     */
    String value();

}
