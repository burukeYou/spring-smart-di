package com.burukeyou.smartdi.proxyspi.annotation;




import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

@Inherited
@Target({ElementType.FIELD,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@AutowiredSPI(proxy = true)
public @interface AutowiredProxySPI {

    @AliasFor(annotation = AutowiredSPI.class)
    boolean required() default false;

}
