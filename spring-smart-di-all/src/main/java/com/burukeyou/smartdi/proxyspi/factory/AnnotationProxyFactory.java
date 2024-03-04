package com.burukeyou.smartdi.proxyspi.factory;

import java.lang.annotation.Annotation;

/**
 * @param <T>
 *
 * @author caizhihao
 */
public interface AnnotationProxyFactory<T extends Annotation> {

    /**
     * @param targetClass        Object type obtained
     * @param spi                spi anno
     * @return
     */
    Object getProxy(Class<?> targetClass, T spi);

}
