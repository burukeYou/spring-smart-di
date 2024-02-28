package com.burukeyou.smartdi.annotations;


import com.burukeyou.smartdi.config.SmartDIConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * enable samrt di function
 * @author caizhihao
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import({SmartDIConfiguration.class})
@Documented
public @interface EnableSmartDI {


}
