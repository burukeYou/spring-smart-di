package com.burukeyou.test.smartdi.extend;


import com.burukeyou.smartdi.proxyspi.annotation.ProxySPI;

import java.lang.annotation.*;

@Inherited
@Target({ElementType.FIELD,ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@ProxySPI(DbProxyFactory.class)
public @interface DBProxySPI {


    String value();

}
