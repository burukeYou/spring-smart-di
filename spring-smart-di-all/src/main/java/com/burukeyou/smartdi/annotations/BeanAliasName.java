package com.burukeyou.smartdi.annotations;



import com.burukeyou.smartdi.config.SpringBeanContext;

import java.lang.annotation.*;

/**
 *    specified bean alias name , then can  obtain through {@link SpringBeanContext}
 */
@Inherited
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface BeanAliasName {


    String value();

}
