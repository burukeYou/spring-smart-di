package com.burukeyou.smartdi.support;

import lombok.Data;

@Data
public class ProxySPIFactoryBeanParam {

    private Class<?> targetClass;
    private AnnotationMeta annotationMeta;

    public ProxySPIFactoryBeanParam(Class<?> targetClass, AnnotationMeta annotationMeta) {
        this.targetClass = targetClass;
        this.annotationMeta = annotationMeta;
    }
}
