package com.burukeyou.smartdi.register;

import com.burukeyou.smartdi.support.AutowiredInvocation;

import java.lang.annotation.Annotation;
import java.util.List;

/**
 * @author  caizhihao
 */
public interface AutowiredBeanProcessor {

    /**
     * Specify the injection annotations that need to be intercepted
     * @return      the custom autowired annotations
     */
    List<Class<? extends Annotation>> interceptAnnotation();

    /**
     * How to obtain the beans that need to be injected
     * @param invocation        Context information when injected
     * @return       get inject bean
     */
    Object getInjectedBean(AutowiredInvocation invocation);
}
