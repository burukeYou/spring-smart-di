package com.burukeyou.smartdi.proxyspi.spi;




import java.lang.annotation.*;

/**
 * @author caizhihao
 */
@Inherited
@Target({ElementType.CONSTRUCTOR, ElementType.METHOD, ElementType.PARAMETER, ElementType.FIELD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface AutowiredSPI {

    boolean required() default false;

}
