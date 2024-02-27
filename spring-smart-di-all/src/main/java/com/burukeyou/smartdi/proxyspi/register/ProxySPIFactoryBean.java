package com.burukeyou.smartdi.proxyspi.register;


import com.burukeyou.smartdi.config.SpringBeanContext;
import com.burukeyou.smartdi.proxyspi.ProxySpiLoader;
import com.burukeyou.smartdi.proxyspi.annotation.ProxySPI;
import com.burukeyou.smartdi.proxyspi.factory.AnnotationProxyFactory;
import com.burukeyou.smartdi.support.AnnotationMeta;
import com.burukeyou.smartdi.support.ProxySPIFactoryBeanParam;
import lombok.Setter;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.target.AbstractLazyCreationTargetSource;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.core.annotation.AnnotatedElementUtils;

import java.lang.annotation.Annotation;

/**
 * @author  caizhihao
 * @param <T>
 */
@Setter
public class ProxySPIFactoryBean<T> extends BaseSpringAware implements FactoryBean<Object> {

    private Object lazyProxy;

    private Class<?> targetClass;

    private AnnotationMeta annotationMeta;

    private ProxySPI proxySPI ;


    public ProxySPIFactoryBean() {
    }

    public ProxySPIFactoryBean(ProxySPIFactoryBeanParam param) {
        this.targetClass = param.getTargetClass();
        this.annotationMeta = param.getAnnotationMeta();
        proxySPI = AnnotatedElementUtils.getMergedAnnotation(targetClass, ProxySPI.class);
    }

    @Override
    public Object getObject() throws Exception {
        if (lazyProxy == null) {
            createLazyProxy();
        }
        return (T) lazyProxy;
    }

    private class ProxySPILazyInitTargetSource extends AbstractLazyCreationTargetSource {

        @Override
        protected Object createObject() throws Exception {
            return getCallProxy();
        }

        @Override
        public synchronized Class<?> getTargetClass() {
            return targetClass;
        }
    }

    private Object getCallProxy() throws Exception {
        return ProxySpiLoader.load(targetClass);
    }

    private void createLazyProxy() {
        ProxyFactory proxyFactory = new ProxyFactory();
        proxyFactory.setTargetSource(new ProxySPILazyInitTargetSource());
        proxyFactory.addInterface(targetClass);

        proxyFactory.addAdvice(new MethodInterceptor() {
            @Override
            public Object invoke(MethodInvocation methodInvocation) throws Throwable {
                Annotation obj = ProxySpiLoader.getSPIObject(targetClass);
                if (obj == null){
                    throw new BeanCreationException(targetClass.getName() + "请标记ProxySPI的注解或者ProxySPI复合注解");
                }

                AnnotationProxyFactory springBean = SpringBeanContext.getBean(proxySPI.value());
                Object bean = springBean.getProxy(targetClass,obj);
                return methodInvocation.getMethod().invoke(bean, methodInvocation.getArguments());
            }
        });

        this.lazyProxy = proxyFactory.getProxy(this.beanClassLoader);
    }

    @Override
    public Class<?> getObjectType() {
        return targetClass;
    }
}
