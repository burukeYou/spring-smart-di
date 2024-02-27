package com.burukeyou.smartdi.config;


import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @author caizhihao
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import({SmartDIConfiguration.class})
@Documented
public @interface EnableSmartDI {


}
