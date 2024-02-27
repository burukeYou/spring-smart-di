package com.burukeyou.smartdi.smart;


import java.lang.annotation.*;

/**
 * @author caizhihao
 *
 */
@Inherited
@Target({ElementType.FIELD,ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
//@CustomAutowired(factory = SmartAutowiredFactory.class,value = SmartAutowired.class)
public @interface SmartAutowired {


    Class<?> defaultType() default Void.class;

    /**
     *
     */
    String propValue() default "";

}
