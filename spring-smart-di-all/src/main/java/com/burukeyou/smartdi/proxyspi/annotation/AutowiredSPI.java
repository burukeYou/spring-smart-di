package com.burukeyou.smartdi.proxyspi.annotation;




import java.lang.annotation.*;

/**
 * @author caizhihao
 */
@Inherited
@Target({ElementType.FIELD,ElementType.METHOD,ElementType.TYPE,ElementType.CONSTRUCTOR})
@Retention(RetentionPolicy.RUNTIME)
public @interface AutowiredSPI {


    boolean required() default false;

    boolean proxy() default false;
}
