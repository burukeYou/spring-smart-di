package com.burukeyou.smartdi.proxyspi.register;

import com.burukeyou.smartdi.proxyspi.ProxySpiLoader;
import com.burukeyou.smartdi.proxyspi.annotation.AutowiredSPI;
import com.burukeyou.smartdi.register.BaseAutowiredBeanFactory;
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
public class AutoProxySPIBeanPostProcessor extends BaseAutowiredBeanFactory {

    @Override
    protected List<Class<? extends Annotation>> processAnnotation() {
        return Collections.singletonList(AutowiredSPI.class);
    }

    @Override
    public   Object doGetInjectedBean(AutowiredInvocation invocation) throws Exception{
        AutowiredSPI autowiredSPI = invocation.getAnnotationMeta().getAnnotationObj();
        Class<?> injectedType = invocation.getInjectedType();
        if (autowiredSPI.proxy()){
            return springContext.getBean(getBeanName(injectedType));
        }else {
            return ProxySpiLoader.load(injectedType);
        }
    }

    @Override
    protected void beforeInjection(AnnotatedInjectionMetadata metadata) {
        for (AnnotatedFieldElement fieldElement : metadata.getFieldElements()) {
//            if (fieldElement.injectedObject != null) {
//                continue;
//            }

            registerBeanDefinition(fieldElement);

            //associate fieldElement and reference bean
            //fieldElement.injectedObject = referenceBeanName;
            //injectedFieldReferenceBeanCache.put(fieldElement, referenceBeanName);

        }


    }

    public void registerBeanDefinition(AnnotatedFieldElement fieldElement){
        Class<?> injectedType = fieldElement.getField().getType();
        AnnotationMeta annotationMeta = fieldElement.getAnnotationMeta();
        RootBeanDefinition beanDefinition = getRootBeanDefinition(injectedType, annotationMeta);
        beanDefinitionRegistry.registerBeanDefinition(getBeanName(injectedType),beanDefinition);
    }


    public static RootBeanDefinition getRootBeanDefinition(Class<?> injectedType, AnnotationMeta annotationMeta) {
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
