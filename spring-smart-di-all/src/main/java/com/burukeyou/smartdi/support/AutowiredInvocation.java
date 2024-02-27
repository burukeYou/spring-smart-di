package com.burukeyou.smartdi.support;

import lombok.Data;

import java.lang.reflect.Member;

@Data
public class AutowiredInvocation {

    private Object targetBean;
    private String targetBeanName;
    private Class<?> injectedType;
    private Member injectedMember;
    private AnnotationMeta annotationMeta;
}
