package com.burukeyou.smartdi.support;

import lombok.Data;
import org.springframework.core.annotation.AnnotationAttributes;

import java.lang.annotation.Annotation;

@Data
public class AnnotationMeta {

    private Annotation annotation;
    private AnnotationAttributes attributes;

    public AnnotationMeta(Annotation annotation, AnnotationAttributes attributes) {
        this.annotation = annotation;
        this.attributes = attributes;
    }

    public <T> T getAnnotationObj(){
        return (T) annotation;
    }
}
