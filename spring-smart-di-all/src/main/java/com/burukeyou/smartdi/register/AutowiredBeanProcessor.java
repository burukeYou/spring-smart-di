package com.burukeyou.smartdi.register;

import com.burukeyou.smartdi.support.AutowiredInvocation;

public interface AutowiredBeanProcessor  {

    /**
        注册bean定义
     */
    void registerBeanDefinition(AutowiredInvocation invocation);

    /**
     *  获取bean
     */
    Object getCustomBean(AutowiredInvocation invocation) throws Exception;
}
