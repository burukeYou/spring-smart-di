package com.burukeyou.smartdi.register;

import com.burukeyou.smartdi.support.AutowiredInvocation;

import java.lang.annotation.Annotation;

public interface AutowiredBeanFactory<T extends Annotation>  {

    /**
        注册bean定义
     */
    void registerBeanDefinition(AutowiredInvocation invocation);

    /**
     *  获取bean
     */
    Object getCustomBean(AutowiredInvocation invocation,T annotation) throws Exception;
}
