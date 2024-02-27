package com.burukeyou.smartdi.annotations;

import com.burukeyou.smartdi.register.AutowiredBeanFactory;
import com.burukeyou.smartdi.register.BaseAutowiredBeanPostProcessor;
import com.burukeyou.smartdi.support.AnnotationMeta;
import com.burukeyou.smartdi.support.AutowiredInvocation;
import com.burukeyou.smartdi.utils.AnnotationUtils;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.util.ReflectionUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

//@Component
public class CustomBeanPostProcessor extends BaseAutowiredBeanPostProcessor {

    @Override
    protected List<Class<? extends Annotation>> processAnnotation() {
        return Collections.singletonList(CustomAutowired.class);
    }


    @Override
    protected Object doGetInjectedBean(AutowiredInvocation invocation) throws Exception {
        AnnotationMeta annotationMeta = invocation.getAnnotationMeta();
        CustomAutowired customAutowired = annotationMeta.getAnnotationObj();
        Class<? extends AutowiredBeanFactory> value = customAutowired.factory();
        AutowiredBeanFactory bean = springContext.getBean(value);

        Class annotationClass = null;
        for (Type genericInterface : value.getGenericInterfaces()) {
            if (genericInterface instanceof ParameterizedType){
                ParameterizedType parameterizedType = (ParameterizedType) genericInterface;
                Type actualTypeArgument = parameterizedType.getActualTypeArguments()[0];
                annotationClass =  (Class) actualTypeArgument;
                break;
            }
        }
        return bean.getCustomBean(invocation,annotationMeta.getAnnotation());
    }

    @Override
    protected List<AnnotatedFieldElement> findFieldAnnotationMetadata(Class<?> beanClass) {
        List<AnnotatedFieldElement> elements = new ArrayList<>();
        ReflectionUtils.doWithFields(beanClass,(field -> {
            CustomAutowired customAutowired = AnnotatedElementUtils.findMergedAnnotation(field, CustomAutowired.class);
            if (customAutowired == null){
                return;
            }

            Class<? extends Annotation> value = customAutowired.value();
            AnnotationMeta annotationMeta = AnnotationUtils.getAnnotationMeta(field, value, getEnvironment(), false, true);
            if (annotationMeta != null) {
                elements.add(new AnnotatedFieldElement(field, annotationMeta));
            }
        }));
        return elements;
    }
}
