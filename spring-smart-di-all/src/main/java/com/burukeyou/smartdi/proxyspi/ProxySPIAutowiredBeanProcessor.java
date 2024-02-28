package com.burukeyou.smartdi.proxyspi;

import com.burukeyou.smartdi.proxyspi.annotation.AutowiredProxySPI;
import com.burukeyou.smartdi.register.BaseAutowiredBeanProcessor;
import com.burukeyou.smartdi.support.AnnotationMeta;
import com.burukeyou.smartdi.support.AutowiredInvocation;
import com.burukeyou.smartdi.support.ProxySPIFactoryBeanParam;
import org.springframework.beans.factory.config.ConstructorArgumentValues;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.stereotype.Component;

import java.beans.Introspector;
import java.lang.annotation.Annotation;
import java.util.Collections;
import java.util.List;


/**
 * @author  caizhihao
 */
@Component
public class ProxySPIAutowiredBeanProcessor extends BaseAutowiredBeanProcessor {

    @Override
    public List<Class<? extends Annotation>> filterAnnotation() {
        return Collections.singletonList(AutowiredProxySPI.class);
    }

    @Override
    public Object getInjectedBean(AutowiredInvocation invocation) {
        Class<?> injectedType = invocation.getInjectedType();
        return springContext.getBean(getBeanName(injectedType));
    }

    @Override
    protected void beforeInjection(AnnotatedInjectionMetadata metadata) {
        for (AnnotatedFieldElement fieldElement : metadata.getFieldElements()) {
            Class<?> injectedType = fieldElement.getField().getType();
            AnnotationMeta annotationMeta = fieldElement.getAnnotationMeta();
            RootBeanDefinition beanDefinition = getRootBeanDefinition(injectedType, annotationMeta);
            beanDefinitionRegistry.registerBeanDefinition(getBeanName(injectedType),beanDefinition);
        }
    }


    public RootBeanDefinition getRootBeanDefinition(Class<?> injectedType, AnnotationMeta annotationMeta) {
        RootBeanDefinition beanDefinition = new RootBeanDefinition();
        beanDefinition.setBeanClass(ProxySPIFactoryBean.class);
        ConstructorArgumentValues constructorArgumentValues = beanDefinition.getConstructorArgumentValues();
        ProxySPIFactoryBeanParam param = new ProxySPIFactoryBeanParam(injectedType,annotationMeta);
        constructorArgumentValues.addGenericArgumentValue(param);
        beanDefinition.setPrimary(true);
        beanDefinition.setAutowireMode(AbstractBeanDefinition.AUTOWIRE_BY_TYPE);
        return beanDefinition;
    }

    public String getBeanName(Class<?> injectedType){
        return Introspector.decapitalize(injectedType.getSimpleName());
    }

}
