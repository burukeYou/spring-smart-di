package com.burukeyou.smartdi.annotations;


import com.burukeyou.smartdi.register.AutowiredBeanFactory;

import java.lang.annotation.*;

/**
 * @author caizhihao
 */
@Inherited
@Target({ElementType.FIELD,ElementType.METHOD,ElementType.TYPE,ElementType.CONSTRUCTOR,ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface CustomAutowired {

    Class<? extends Annotation> value();

    Class<? extends AutowiredBeanFactory<?>> factory();


}
