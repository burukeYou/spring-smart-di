package com.burukeyou.smartdi.annotations;



import java.lang.annotation.*;

@Inherited
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface BeanAliasName {


    String value();

}
