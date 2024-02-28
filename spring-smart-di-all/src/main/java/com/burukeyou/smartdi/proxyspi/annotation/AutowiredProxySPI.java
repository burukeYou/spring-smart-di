package com.burukeyou.smartdi.proxyspi.annotation;


import java.lang.annotation.*;

@Inherited
@Target({ElementType.CONSTRUCTOR, ElementType.METHOD, ElementType.PARAMETER, ElementType.FIELD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface AutowiredProxySPI {

   // boolean required() default false;

}
