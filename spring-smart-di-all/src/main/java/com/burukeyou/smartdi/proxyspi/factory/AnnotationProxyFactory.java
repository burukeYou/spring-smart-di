package com.burukeyou.smartdi.proxyspi.factory;

import java.lang.annotation.Annotation;

/**
 * @param <T>
 *
 * @author caizhihao
 */
public interface AnnotationProxyFactory<T extends Annotation> {

    /**
     * @param targetClass       获取的代理类型
     * @param spi
     * @return
     */
    Object getProxy(Class<?> targetClass, T spi);

}
