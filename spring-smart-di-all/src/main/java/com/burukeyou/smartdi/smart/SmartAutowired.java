package com.burukeyou.smartdi.smart;


import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

/**
 * @author caizhihao
 *
 */
@Inherited
@Target({ElementType.CONSTRUCTOR, ElementType.METHOD, ElementType.PARAMETER, ElementType.FIELD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface SmartAutowired {

    /**
     * if config,use the environment key value to get  inject bean
     */
    @AliasFor("value")
    String key() default "";

    @AliasFor("key")
    String value() default "";

    /**
     * Declares whether the annotated dependency is required.
     */
    boolean required() default true;
}
