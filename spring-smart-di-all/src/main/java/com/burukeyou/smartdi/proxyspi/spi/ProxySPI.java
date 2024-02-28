package com.burukeyou.smartdi.proxyspi.spi;


import com.burukeyou.smartdi.proxyspi.factory.AnnotationProxyFactory;

import java.lang.annotation.*;

@Inherited
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ProxySPI {


    Class<? extends AnnotationProxyFactory<?>> value();


}
